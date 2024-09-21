package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Agendamento;
import com.ultracar.automaintenance.automaintenancemanager.enums.StatusType;
import java.time.format.DateTimeFormatter;

/**
 * The type Agendamento dto.
 */
public record AgendamentoDto(
    Long id,
    Long clienteId,
    String dataAgendamento,
    String descricaoServico,
    StatusType status
) {

  /**
   * From entity agendamento dto.
   *
   * @param agendamento the agendamento
   * @return the agendamento dto
   */
  public static AgendamentoDto fromEntity(Agendamento agendamento) {

    return new AgendamentoDto(
        agendamento.getId(),
        agendamento.getCliente().getId(),
        agendamento.getDataAgendamento()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
        agendamento.getDescricaoServico(),
        agendamento.getStatus()
    );
  }
}
