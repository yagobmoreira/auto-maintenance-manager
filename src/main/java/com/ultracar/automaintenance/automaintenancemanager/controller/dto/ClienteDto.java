package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.entity.Endereco;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Cliente dto.
 */
public record ClienteDto(Long id, String nome, String cpf, List<Veiculo> veiculos,
                         Endereco endereco, List<AgendamentoDto> agendamentos,
                         LocalDate createDate, LocalDate lastModifiedDate) {

  /**
   * From entity cliente dto.
   *
   * @param cliente the cliente
   * @return the cliente dto
   */
  public static ClienteDto fromEntity(Cliente cliente) {
    return new ClienteDto(
        cliente.getId(),
        cliente.getNome(),
        cliente.getCpf(),
        cliente.getVeiculos(),
        cliente.getEndereco(),
        cliente.getAgendamentos().stream().map(AgendamentoDto::fromEntity).toList(),
        cliente.getCreateDate(),
        cliente.getLastModifiedDate()
    );
  }

}
