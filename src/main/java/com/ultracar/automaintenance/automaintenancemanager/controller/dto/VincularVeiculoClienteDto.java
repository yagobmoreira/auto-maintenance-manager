package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * The type Vincular veiculo cliente dto.
 */
public record VincularVeiculoClienteDto(
    @NotBlank(message = "Campo placa é obrigatório") String placa) {

}
