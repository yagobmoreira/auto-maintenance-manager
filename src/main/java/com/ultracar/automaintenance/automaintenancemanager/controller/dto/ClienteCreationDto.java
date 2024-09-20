package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteCreationDto(
    @NotBlank(message = "Campo nome obrigatório") String nome,
    @NotBlank @CPF(message = "Campo CPF inválido") String cpf,
    @NotBlank(message = "Campo cep é obrigatório") String cep
) {
    public Cliente toEntity() {
        return new Cliente(nome, cpf);
    }
}
