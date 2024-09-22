package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import java.time.LocalDate;

/**
 * The type Veiculo dto.
 */
public record VeiculoDto(
    Long id,
    String placa,
    String modelo,
    String marca,
    Integer ano,
    LocalDate createDate,
    LocalDate lastModifiedDate
) {

  /**
   * From entity veiculo dto.
   *
   * @param veiculo the veiculo
   * @return the veiculo dto
   */
  public static VeiculoDto fromEntity(Veiculo veiculo) {
    return new VeiculoDto(
        veiculo.getId(),
        veiculo.getPlaca(),
        veiculo.getModelo(),
        veiculo.getMarca(),
        veiculo.getAno(),
        veiculo.getCreateDate(),
        veiculo.getLastModifiedDate()
    );
  }
}
