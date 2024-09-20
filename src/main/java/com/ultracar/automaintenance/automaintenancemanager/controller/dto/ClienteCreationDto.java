package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteCreationDto(
    @NotBlank(message = "Campo nome é obrigatório") String nome,
    @NotBlank @CPF(message = "Campo CPF é inválido") String cpf,
    @NotBlank(message = "Campo cep é obrigatório") String cep
) {
    public Cliente toEntity() {
        return new Cliente(nome, cpf);
    }
}
