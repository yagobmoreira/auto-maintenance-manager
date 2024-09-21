package com.ultracar.automaintenance.automaintenancemanager.service.impl;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.entity.Endereco;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.repository.ClienteRepository;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.VeiculoNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço de Cliente.
 */
@Service
public class ClienteServiceImpl {

  private final ClienteRepository clienteRepository;
  private final EnderecoServiceImpl enderecoService;
  private final VeiculoServiceImpl veiculoService;

  /**
   * Instantiates a new Cliente service.
   *
   * @param clienteRepository the cliente repository
   * @param enderecoService   the endereco service
   * @param veiculoService    the veiculo service
   */
  @Autowired
  public ClienteServiceImpl(ClienteRepository clienteRepository,
      EnderecoServiceImpl enderecoService, VeiculoServiceImpl veiculoService) {
    this.clienteRepository = clienteRepository;
    this.enderecoService = enderecoService;
    this.veiculoService = veiculoService;
  }

  /**
   * Lista todos os clientes.
   *
   * @return uma lista de clientes.
   */
  public List<Cliente> findAll() {
    return this.clienteRepository.findAll();
  }

  /**
   * /Procura um cliente pelo ‘ID’.
   *
   * @param clienteId 'ID' do cliente.
   * @return Objeto do tipo Cliente
   * @throws ClienteNotFoundException Caso não exista um cliente com o 'ID' indicado
   */
  public Cliente findById(Long clienteId) throws ClienteNotFoundException {
    Cliente dbClient = this.clienteRepository.findById(clienteId)
                           .orElseThrow(ClienteNotFoundException::new);

    if (dbClient.isDeleted()) {
      throw new ClienteNotFoundException();
    }

    return dbClient;
  }

  /**
   * Cria um cliente.
   *
   * @param cliente  Objeto do tipo Cliente
   * @param endereco Objeto do tipo Endereco
   * @param veiculo  Objeto do tipo Veiculo
   * @return Objeto do tipo Cliente
   * @throws BusinessException Caso o veículo já esteja cadastrado.
   */
  @Transactional
  public Cliente create(Cliente cliente, Endereco endereco, Veiculo veiculo)
      throws BusinessException {
    //Verifica se o CPF é de uma pessoa já cadastrada
    validateCliente(cliente);

    //Cria no banco de dados o veículo do cliente. Caso veículo já esteja cadastrado lança um erro.
    Veiculo veiculoCriado = veiculoService.criarVeiculoComDono(veiculo, cliente);

    //Após a criação no banco de dados. Víncula ao objeto Cliente o Veículo criado.
    cliente.setVeiculo(veiculoCriado);

    //Cria no banco de dados o Cliente.
    Cliente clienteCriado = clienteRepository.save(cliente);

    //Cria um endereço na tabela endereços,
    //víncula esse endereço ao cliente e atualiza o cliente no banco de dados.
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

  /**
   * Atualizar informações pessoais do cliente.
   *
   * @param clienteId o 'ID' do cliente
   * @param entity    Objeto do tipo Cliente
   * @return Objeto do tipo Cliente atualizado
   * @throws BusinessException        Caso o id passado, seja diferente do 'ID' do cliente.
   * @throws ClienteNotFoundException Caso não exista um cliente com o 'ID' indicado
   */
  @Transactional
  public Cliente update(Long clienteId, Cliente entity)
      throws BusinessException, ClienteNotFoundException {
    Cliente dbCliente = this.findById(clienteId);

    if (!dbCliente.getId().equals(entity.getId())) {
      throw new BusinessException("O id deve ser o mesmo");
    }

    if (dbCliente.isDeleted()) {
      throw new ClienteNotFoundException();
    }

    //Atualiza informações do cliente, como CPF e nome;
    dbCliente.setCpf(entity.getCpf());
    dbCliente.setNome(entity.getNome());

    //Salva no banco de dados, as informações atualizadas do cliente.
    return this.clienteRepository.save(dbCliente);
  }


  /**
   * Atualiza o campo deleted.
   *
   * @param clienteId o 'ID' do cliente
   * @throws ClienteNotFoundException Caso não exista um cliente com o 'ID' indicado
   */
  @Transactional
  public void delete(Long clienteId) throws ClienteNotFoundException {
    Cliente dbCliente = this.findById(clienteId);
    dbCliente.setDeleted();

    this.clienteRepository.save(dbCliente);
  }

  /**
   * Adicionar veiculo cliente.
   *
   * @param placa     the placa
   * @param clienteId the cliente id
   * @return the cliente
   * @throws ClienteNotFoundException the cliente not found exception
   * @throws VeiculoNotFoundException the veiculo not found exception
   */
  @Transactional
  public Cliente adicionarVeiculo(String placa, Long clienteId)
      throws ClienteNotFoundException, VeiculoNotFoundException {
    Cliente dbCliente = this.findById(clienteId);
    Veiculo veiculo = this.veiculoService.listarPorPlaca(placa);

    dbCliente.setVeiculo(veiculo);
    this.veiculoService.adicionarCliente(dbCliente, veiculo);

    return this.clienteRepository.save(dbCliente);
  }
}
