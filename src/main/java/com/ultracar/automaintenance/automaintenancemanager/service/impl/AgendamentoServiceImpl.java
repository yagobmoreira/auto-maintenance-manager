package com.ultracar.automaintenance.automaintenancemanager.service.impl;

import com.ultracar.automaintenance.automaintenancemanager.entity.Agendamento;
import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.enums.StatusType;
import com.ultracar.automaintenance.automaintenancemanager.repository.AgendamentoRepository;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.AgendamentoNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoServiceImpl {

    private final ClienteServiceImpl clienteService;
    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    public AgendamentoServiceImpl(ClienteServiceImpl clienteService,
        AgendamentoRepository agendamentoRepository) {
        this.clienteService = clienteService;
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<Agendamento> findAll() {
        return this.agendamentoRepository.findAll();
    }

    public Agendamento findById(Long agendamentoId) throws AgendamentoNotFoundException {
        return this.agendamentoRepository.findById(agendamentoId).orElseThrow(
            AgendamentoNotFoundException::new);
    }

    @Transactional
    public Agendamento create(Agendamento novoAgendamento, Long clienteId)
        throws ClienteNotFoundException {
        Cliente cliente = this.clienteService.findById(clienteId);

        novoAgendamento.setCliente(cliente);
        cliente.setAgendamento(novoAgendamento);

        clienteService.saveAgendamento(cliente);

        return this.agendamentoRepository.save(novoAgendamento);
    }

    @Transactional
    public Agendamento finalizarServico(Long agendamentoId)
        throws AgendamentoNotFoundException, BusinessException {
        Agendamento agendamento = this.findById(agendamentoId);

        if (!agendamento.getStatus().equals(StatusType.PENDENTE)) {
            throw new BusinessException("Erro ao finalizar o agendamento. Agendamento já realizado ou cancelado anteriormente");
        }

        agendamento.setStatus(StatusType.REALIZADO);

        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public Agendamento cancelarServico(Long agendamentoId)
        throws AgendamentoNotFoundException, BusinessException {
        Agendamento agendamento = this.findById(agendamentoId);

        if (!agendamento.getStatus().equals(StatusType.PENDENTE)) {
            throw new BusinessException("Erro ao cancelar o agendamento. Agendamento já realizado ou cancelado anteriormente");
        }

        agendamento.setStatus(StatusType.CANCELADO);

        return agendamentoRepository.save(agendamento);
    }
}
