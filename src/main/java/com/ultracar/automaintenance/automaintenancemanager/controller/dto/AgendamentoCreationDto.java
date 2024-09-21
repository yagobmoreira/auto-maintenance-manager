package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Agendamento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * The type Agendamento creation dto.
 */
public record AgendamentoCreationDto(
    @Future(message = "A data do agendamento deve ser superior à data atual")
    @NotNull(message = "Campo data do agendamento é obrigatório") LocalDateTime dataAgendamento,
    @NotBlank(message = "Campo descrição do serviço é obrigatório") String descricaoServico) {

  public Agendamento toEntity() {
    return new Agendamento(dataAgendamento, descricaoServico);
  }
}
