package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Agendamento;
import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.enums.StatusType;
import java.time.LocalDateTime;

public record AgendamentoDto(
    Long id,
    Long clienteId,
    LocalDateTime dataAgendamento,
    String descricaoServico,
    StatusType status
) {
    public static
    AgendamentoDto fromEntity(Agendamento agendamento) {
        return new AgendamentoDto(
            agendamento.getId(),
            agendamento.getCliente().getId(),
            agendamento.getDataAgendamento(),
            agendamento.getDescricaoServico(),
            agendamento.getStatus()
        );
    }
}
