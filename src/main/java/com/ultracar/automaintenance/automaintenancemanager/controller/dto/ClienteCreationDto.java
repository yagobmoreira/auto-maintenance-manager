package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;

public record ClienteCreationDto(
    String nome,
    String cpf,
    String cep
) {
    public Cliente toEntity() {
        return new Cliente(nome, cpf);
    }
}
