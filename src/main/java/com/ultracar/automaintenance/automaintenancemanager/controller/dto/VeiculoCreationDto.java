package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * The type Veiculo creation dto.
 */
public record VeiculoCreationDto(
    @NotBlank(message = "Campo placa é obrigatório") String placa,
    @NotBlank(message = "Campo modelo é obrigatório") String modelo,
    @NotBlank(message = "Campo marca é obrigatório") String marca,
    @NotNull(message = "Campo ano é obrigatório") Integer ano
) {

  /**
   * To entity veiculo.
   *
   * @return the veiculo
   */
  public Veiculo toEntity() {
    return new Veiculo(placa, modelo, marca, ano);
  }
}
