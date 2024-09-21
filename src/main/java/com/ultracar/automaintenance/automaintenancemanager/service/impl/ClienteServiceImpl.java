package com.ultracar.automaintenance.automaintenancemanager.service.impl;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.entity.Endereco;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.repository.ClienteRepository;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl {

    private final ClienteRepository clienteRepository;
    private final EnderecoServiceImpl enderecoService;
    private final VeiculoServiceImpl veiculoService;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository,
        EnderecoServiceImpl enderecoService, VeiculoServiceImpl veiculoService) {
        this.clienteRepository = clienteRepository;
        this.enderecoService = enderecoService;
        this.veiculoService = veiculoService;
    }

    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    public Cliente findById(Long clienteId) throws ClienteNotFoundException {
        Cliente dbClient = this.clienteRepository.findById(clienteId)
            .orElseThrow(ClienteNotFoundException::new);

        if (dbClient.isDeleted()) {
            throw new ClienteNotFoundException();
        }

        return dbClient;
    }

    @Transactional
    public Cliente create(Cliente cliente, Endereco endereco, Veiculo veiculo)
        throws BusinessException {
        //Verifica se o CPF é de uma pessoa já cadastrada
        validateCliente(cliente);

        //Cria no banco de dados o veículo do cliente. Caso veículo já esteja cadastrado lança um erro.
        Veiculo veiculoCriado = veiculoService.create(veiculo);

        //Após a criação no banco de dados. Víncula ao objeto Cliente o Veículo criado.
        cliente.setVeiculo(veiculoCriado);

        //Cria no banco de dados o Cliente.
        Cliente clienteCriado = clienteRepository.save(cliente);

        //Cria um endereço na tabela endereços, víncula esse endereço ao cliente e atualiza o cliente no banco de dados.
        return salvarEndereco(clienteCriado, endereco);
    }

    private void validateCliente(Cliente cliente) throws BusinessException {
        if (clienteRepository.existsClienteByCpf(cliente.getCpf())) {
            throw new BusinessException("CPF já cadastrado anteriormente");
        }
    }

    private Cliente salvarEndereco(Cliente cliente, Endereco endereco) {
        //Adiciona ao objeto endereço o cliente.
        endereco.setCliente(cliente);

        //Salva o endereço na tabela endereços do banco de dados
        Endereco enderecoCriado = enderecoService.create(endereco);

        //Vínculo o endereço ao cliente.
        cliente.setEndereco(enderecoCriado);

        //Salva no banco de dados, o endereço do cliente.
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente update(Long clienteId, Cliente entity)
        throws BusinessException, ClienteNotFoundException {
        Cliente dbCliente = this.findById(clienteId);

        if (!dbCliente.getId().equals(entity.getId())) {
            throw new BusinessException("O id deve ser o mesmo");
        }

        //Atualiza informações do cliente, como CPF e nome;
        dbCliente.setCpf(entity.getCpf());
        dbCliente.setNome(entity.getNome());

        //Salva no banco de dados, as informações atualizadas do cliente.
        return this.clienteRepository.save(dbCliente);
    }

    @Transactional
    public void delete(Long clienteId) throws ClienteNotFoundException {
        Cliente dbCliente = this.findById(clienteId);
        dbCliente.setDeleted();

        this.clienteRepository.save(dbCliente);
    }

    public Cliente saveAgendamento(Cliente clienteComAgendamento) {
        return this.clienteRepository.save(clienteComAgendamento);
    }
}
