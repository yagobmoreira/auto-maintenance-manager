package com.ultracar.automaintenance.automaintenancemanager.service.exception;

public class VeiculoNotFoundException extends NotFoundException {
    public VeiculoNotFoundException() {
        super("Veiculo não encontrado!");
    }
}
