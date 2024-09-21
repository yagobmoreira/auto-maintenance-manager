package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import java.time.LocalDateTime;

/**
 * The type Agendamentos cliente dto.
 */
public record AgendamentosClienteDto(LocalDateTime dataInicial, LocalDateTime dataFinal) {

}
