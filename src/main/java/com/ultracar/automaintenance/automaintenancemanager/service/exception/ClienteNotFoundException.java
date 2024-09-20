package com.ultracar.automaintenance.automaintenancemanager.service.exception;

public class ClienteNotFoundException extends NotFoundException {
    public ClienteNotFoundException() {
        super("Cliente não encontrado!");
    }
}
