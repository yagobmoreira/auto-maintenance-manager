package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

/**
 * The type Cliente creation dto.
 */
public record ClienteCreationDto(
    @NotBlank(message = "Campo nome é obrigatório") String nome,
    @NotBlank @CPF(message = "Campo CPF é inválido") String cpf,
    @NotBlank(message = "Campo cep é obrigatório") String cep,
    @NotBlank(message = "Campo placa é obrigatório") String placa,
    @NotBlank(message = "Campo modelo é obrigatório") String modelo,
    @NotBlank(message = "Campo marca é obrigatório") String marca,
    @NotNull(message = "Campo ano é obrigatório") Integer ano

) {

  public Cliente toEntity() {
    return new Cliente(nome, cpf);
  }
}
