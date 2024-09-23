package com.ultracar.automaintenance.automaintenancemanager.service.impl;

import com.ultracar.automaintenance.automaintenancemanager.entity.Endereco;
import com.ultracar.automaintenance.automaintenancemanager.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço de Endereco.
 */
@Service
public class EnderecoServiceImpl {

  private final EnderecoRepository enderecoRepository;

  @Autowired
  private EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
    this.enderecoRepository = enderecoRepository;
  }

  /**
   * Cria um endereco.
   *
   * @param novoEndereco Objeto do tipo Endereco
   * @return Objeto do tipo Endereco
   */
  public Endereco criarEndereco(Endereco novoEndereco) {
    return this.enderecoRepository.save(novoEndereco);
  }
}
