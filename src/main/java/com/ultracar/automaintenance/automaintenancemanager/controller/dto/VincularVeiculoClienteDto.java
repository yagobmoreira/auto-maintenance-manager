package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record VincularVeiculoClienteDto(
    @NotBlank(message = "Campo placa é obrigatório") String placa) {
}
