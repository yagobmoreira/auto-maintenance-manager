package com.ultracar.automaintenance.automaintenancemanager.controller;

import com.ultracar.automaintenance.automaintenancemanager.controller.dto.AgendamentoCreationDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.AgendamentoDto;
import com.ultracar.automaintenance.automaintenancemanager.entity.Agendamento;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.AgendamentoNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.impl.AgendamentoServiceImpl;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

  private final AgendamentoServiceImpl agendamentoService;

  @Autowired
  public AgendamentoController(AgendamentoServiceImpl agendamentoService) {
    this.agendamentoService = agendamentoService;
  }

  @GetMapping
  public ResponseEntity<List<AgendamentoDto>> findAll() {
    List<Agendamento> agendamentos = agendamentoService.findAll();
    return ResponseEntity.ok(agendamentos.stream().map(AgendamentoDto::fromEntity).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AgendamentoDto> findById(@PathVariable Long id)
      throws AgendamentoNotFoundException {
    Agendamento agendamento = agendamentoService.findById(id);

    return ResponseEntity.ok(AgendamentoDto.fromEntity(agendamento));
  }

  @PostMapping("/clientes/{clienteId}")
  public ResponseEntity<AgendamentoDto> create(
      @RequestBody @Valid AgendamentoCreationDto creationDto, @PathVariable Long clienteId)
      throws ClienteNotFoundException, BusinessException {
    Agendamento novoAgendamento = creationDto.toEntity();

    Agendamento agendamentoCriado = agendamentoService.create(novoAgendamento, clienteId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/clientes/{clienteId}")
                     .buildAndExpand(novoAgendamento.getId()).toUri();

    return ResponseEntity.created(location).body(AgendamentoDto.fromEntity(agendamentoCriado));
  }

  @PatchMapping("/{agendamentoId}/finalizar")
  public ResponseEntity<AgendamentoDto> finalizarServico(@PathVariable Long agendamentoId)
      throws AgendamentoNotFoundException, BusinessException {
    Agendamento agendamentoFinalizado = agendamentoService.finalizarServico(agendamentoId);

    return ResponseEntity.ok(AgendamentoDto.fromEntity(agendamentoFinalizado));
  }

  @PatchMapping("/{agendamentoId}/cancelar")
  public ResponseEntity<AgendamentoDto> cancelarServico(@PathVariable Long agendamentoId)
      throws AgendamentoNotFoundException, BusinessException {
    Agendamento agendamentoCancelado = agendamentoService.cancelarServico(agendamentoId);

    return ResponseEntity.ok(AgendamentoDto.fromEntity(agendamentoCancelado));
  }
}
