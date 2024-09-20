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
    public Cliente create(Cliente cliente, Endereco endereco, Veiculo veiculo) throws BusinessException {
        validateCliente(cliente);

        Cliente clienteCriado = clienteRepository.save(cliente);

        return saveEnderecoAndVeiculo(clienteCriado, endereco, veiculo);
    }

    private void validateCliente(Cliente cliente) throws BusinessException {
        if (clienteRepository.existsClienteByCpf(cliente.getCpf())) {
            throw new BusinessException("CPF já cadastrado anteriormente");
        }
    }

    private Cliente saveEnderecoAndVeiculo(Cliente cliente, Endereco endereco, Veiculo veiculo)
        throws BusinessException {
        endereco.setCliente(cliente);
        veiculo.setCliente(cliente);

        Endereco enderecoCriado = enderecoService.create(endereco);
        Veiculo veiculoCriado = veiculoService.create(veiculo);

        cliente.setEndereco(enderecoCriado);
        cliente.setVeiculo(veiculoCriado);

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente update(Long clienteId, Cliente entity)
        throws BusinessException, ClienteNotFoundException {
        Cliente dbCliente = this.findById(clienteId);

        if (!dbCliente.getId().equals(entity.getId())) {
            throw new BusinessException("O id deve ser o mesmo");
        }

        dbCliente.setCpf(entity.getCpf());
        dbCliente.setNome(entity.getNome());

        return this.clienteRepository.save(dbCliente);
    }

    @Transactional
    public void delete(Long clienteId) throws ClienteNotFoundException {
        Cliente dbCliente = this.findById(clienteId);
        dbCliente.setDeleted();

        this.clienteRepository.save(dbCliente);
    }
}
