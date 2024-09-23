package com.ultracar.automaintenance.automaintenancemanager.service.impl;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.repository.VeiculoRepository;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.VeiculoNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço de Veículo.
 */
@Service
public class VeiculoServiceImpl {

  private final VeiculoRepository veiculoRepository;

  /**
   * Instantiates a new Veiculo service.
   *
   * @param veiculoRepository the veiculo repository
   */
  @Autowired
  public VeiculoServiceImpl(VeiculoRepository veiculoRepository) {
    this.veiculoRepository = veiculoRepository;
  }

  /**
   * Lista todos os veículos.
   *
   * @return uma lista de veículos.
   */
  public List<Veiculo> obterVeiculos() {
    return this.veiculoRepository.findAll();
  }

  /**
   * Procura um cliente pelo ‘ID’.
   *
   * @param veiculoId 'ID' do veículo
   * @return Objeto do tipo Veiculo
   * @throws VeiculoNotFoundException Caso não exista um veículo com o 'ID' indicado
   */
  public Veiculo obterVeiculoPeloId(Long veiculoId) throws VeiculoNotFoundException {
    return this.veiculoRepository.findById(veiculoId).orElseThrow(
        VeiculoNotFoundException::new);
  }

  /**
   * Cria veiculo.
   *
   * @param novoVeiculo Objeto do tipo Veículo
   * @return Objeto do tipo Veiculo
   * @throws BusinessException Caso já exista um veículo cadastrado com a mesma placa.
   */
  @Transactional
  public Veiculo criarVeiculo(Veiculo novoVeiculo) throws BusinessException {
    verificarPlacaCadastrada(novoVeiculo.getPlaca());

    return this.veiculoRepository.save(novoVeiculo);
  }

  /**
   * Criar veiculo com dono veiculo.
   *
   * @param novoVeiculo the novo veiculo
   * @param donoVeiculo the dono veiculo
   * @return the veiculo
   * @throws BusinessException the business exception
   */
  public Veiculo criarVeiculoComDono(Veiculo novoVeiculo, Cliente donoVeiculo)
      throws BusinessException {
    verificarPlacaCadastrada(novoVeiculo.getPlaca());

    Veiculo veiculo = new Veiculo(novoVeiculo.getPlaca(), novoVeiculo.getModelo(),
        novoVeiculo.getMarca(), novoVeiculo.getAno());
    veiculo.setCliente(donoVeiculo);

    return this.veiculoRepository.save(veiculo);
  }

  private void verificarPlacaCadastrada(String placa) throws BusinessException {
    if (veiculoRepository.existsByPlaca(placa)) {
      throw new BusinessException("Veículo já cadastrado com a placa: " + placa);
    }
  }

  /**
   * Listar por placa veiculo.
   *
   * @param placa the placa
   * @return the veiculo
   * @throws VeiculoNotFoundException the veiculo not found exception
   */
  public Veiculo listarPorPlaca(String placa) throws VeiculoNotFoundException {
    return this.veiculoRepository.findByPlaca(placa).orElseThrow(VeiculoNotFoundException::new);
  }

  /**
   * Adicionar cliente.
   *
   * @param cliente the cliente
   * @param veiculo the veiculo
   * @throws VeiculoNotFoundException the veiculo not found exception
   */
  public void adicionarCliente(Cliente cliente, Veiculo veiculo) throws VeiculoNotFoundException {
    Veiculo dbVeiculo = obterVeiculoPeloId(veiculo.getId());
    dbVeiculo.setCliente(cliente);

    this.veiculoRepository.save(dbVeiculo);
  }

}
