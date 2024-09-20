package com.ultracar.automaintenance.automaintenancemanager.controller.dto;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;

public record VeiculoDto(
    Long id,
    String placa,
    String modelo,
    String marca,
    Integer ano,
    Long clienteId
) {
    public static VeiculoDto fromEntity(Veiculo veiculo) {
        return new VeiculoDto(
            veiculo.getId(),
            veiculo.getPlaca(),
            veiculo.getModelo(),
            veiculo.getMarca(),
            veiculo.getAno(),
            veiculo.getCliente().getId()
        );
    }
}
