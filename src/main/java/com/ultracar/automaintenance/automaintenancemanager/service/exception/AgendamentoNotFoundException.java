package com.ultracar.automaintenance.automaintenancemanager.service.exception;

public class AgendamentoNotFoundException extends NotFoundException {
    public AgendamentoNotFoundException() {
        super("Agendamento não encontrado!");
    }
}
