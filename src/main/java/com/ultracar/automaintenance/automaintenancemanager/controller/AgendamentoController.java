package com.ultracar.automaintenance.automaintenancemanager.controller;

import com.ultracar.automaintenance.automaintenancemanager.controller.dto.AgendamentoCreationDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.AgendamentoDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.AgendamentosClienteDto;
import com.ultracar.automaintenance.automaintenancemanager.entity.Agendamento;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.AgendamentoNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.impl.AgendamentoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

/**
 * The type Agendamento controller.
 */
@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

  private final AgendamentoServiceImpl agendamentoService;

  /**
   * Instantiates a new Agendamento controller.
   *
   * @param agendamentoService the agendamento service
   */
  @Autowired
  public AgendamentoController(AgendamentoServiceImpl agendamentoService) {
    this.agendamentoService = agendamentoService;
  }

  /**
   * Listar todos os agendamentos.
   *
   * @return DTO de agendamentos
   */
  @GetMapping
  @Operation(summary = "Buscar agendamentos.", description = "Listar todos os agendamentos.")
  @ApiResponse(responseCode = "200", description = "Retorno de todos os agendamentos.",
      content = @Content(array = @ArraySchema(
          schema = @Schema(implementation = AgendamentoDto.class))))
  public ResponseEntity<List<AgendamentoDto>> obterAgendamentos() {
    List<Agendamento> agendamentos = agendamentoService.obterAgendamentos();
    return ResponseEntity.ok(agendamentos.stream().map(AgendamentoDto::fromEntity).toList());
  }

  /**
   * Lista um agendamento pelo 'ID'.
   *
   * @param id 'ID' do agendamento
   * @return DTO do agendamento
   * @throws AgendamentoNotFoundException Caso não encontre o agendamento
   */
  @GetMapping("/{id}")
  @Operation(summary = "Buscar agendamento.", description = "Buscar um agendamento pelo ID.")
  @ApiResponse(responseCode = "200", description = "Retorno do agendamento.",
      content = @Content(schema = @Schema(implementation = AgendamentoDto.class)))
  @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
  public ResponseEntity<AgendamentoDto> obterAgendamentoPeloId(@PathVariable Long id)
      throws AgendamentoNotFoundException {
    Agendamento agendamento = agendamentoService.obterAgendamentoPeloId(id);

    return ResponseEntity.ok(AgendamentoDto.fromEntity(agendamento));
  }

  /**
   * Criar um agendamento.
   *
   * @param creationDto DTO de criação de agendamento
   * @param clienteId   'ID' do cliente
   * @return DTO do agendamento criado
   * @throws ClienteNotFoundException Caso não encontre o cliente
   * @throws BusinessException        Caso a data seja inválida
   */
  @PostMapping("/clientes/{clienteId}")
  @Operation(summary = "Criar agendamento.", description = "Criar um novo agendamento.")
  @ApiResponse(responseCode = "201", description = "Agendamento criado.",
      content = @Content(schema = @Schema(implementation = AgendamentoDto.class)))
  @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
  @ApiResponse(responseCode = "400", description = "Data inválida.")
  public ResponseEntity<AgendamentoDto> criarAgendamento(
      @RequestBody @Valid AgendamentoCreationDto creationDto, @PathVariable Long clienteId)
      throws ClienteNotFoundException, BusinessException {
    Agendamento novoAgendamento = creationDto.toEntity();

    Agendamento agendamentoCriado = agendamentoService.criarAgendamento(novoAgendamento, clienteId);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/clientes/{clienteId}")
                       .buildAndExpand(novoAgendamento.getId()).toUri();

    return ResponseEntity.created(location).body(AgendamentoDto.fromEntity(agendamentoCriado));
  }

  /**
   * Finaliza um agendamento. Status 'REALIZADO'.
   *
   * @param agendamentoId 'ID' do agendamento
   * @return DTO do agendamento finalizado
   * @throws AgendamentoNotFoundException Caso não encontre o agendamento
   * @throws BusinessException            Caso o agendamento já tenha sido finalizado ou cancelado
   */
  @PatchMapping("/{agendamentoId}/finalizar")
  @Operation(summary = "Finalizar agendamento.", description = "Finalizar um agendamento.")
  @ApiResponse(responseCode = "200", description = "Agendamento finalizado",
      content = @Content(schema = @Schema(implementation = AgendamentoDto.class)))
  @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
  @ApiResponse(responseCode = "400", description = "Agendamento já finalizado ou cancelado.")
  public ResponseEntity<AgendamentoDto> finalizarServico(@PathVariable Long agendamentoId)
      throws AgendamentoNotFoundException, BusinessException {
    Agendamento agendamentoFinalizado = agendamentoService.finalizarServico(agendamentoId);

    return ResponseEntity.ok(AgendamentoDto.fromEntity(agendamentoFinalizado));
  }

  /**
   * Cancela um agendamento. Status 'CANCELADO'.
   *
   * @param agendamentoId 'ID' do agendamento
   * @return DTO do agendamento cancelado
   * @throws AgendamentoNotFoundException Caso não encontre o agendamento
   * @throws BusinessException            Caso o agendamento já tenha sido finalizado ou cancelado
   */
  @PatchMapping("/{agendamentoId}/cancelar")
  @Operation(summary = "Cancelar agendamento.", description = "Cancelar um agendamento.")
  @ApiResponse(responseCode = "200", description = "Agendamento cancelado.",
      content = @Content(schema = @Schema(implementation = AgendamentoDto.class)))
  @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
  @ApiResponse(responseCode = "400", description = "Agendamento já finalizado ou cancelado.")
  public ResponseEntity<AgendamentoDto> cancelarServico(@PathVariable Long agendamentoId)
      throws AgendamentoNotFoundException, BusinessException {
    Agendamento agendamentoCancelado = agendamentoService.cancelarServico(agendamentoId);

    return ResponseEntity.ok(AgendamentoDto.fromEntity(agendamentoCancelado));
  }

  /**
   * Listar agendamentos entre datas de um cliente.
   *
   * @param clienteId              'ID' do cliente
   * @param agendamentosClienteDto DTO de datas
   * @return Lista de agendamentos
   */
  @GetMapping("/clientes/{clienteId}")
  @Operation(summary = "Buscar agendamentos de um cliente.",
      description = "Listar todos os agendamentos de um cliente entre datas.")
  @ApiResponse(responseCode = "200", description = "Retorno todos os agendamentos de um cliente.",
      content = @Content(array = @ArraySchema(
          schema = @Schema(implementation = AgendamentoDto.class)))
  )
  public ResponseEntity<List<AgendamentoDto>> listarAgendamentosEntreDatas(
      @PathVariable Long clienteId,
      @RequestBody AgendamentosClienteDto agendamentosClienteDto) {
    List<Agendamento> agendamentos = this.agendamentoService.listarAgendamentoEntreDatas(clienteId,
        agendamentosClienteDto.dataInicial(), agendamentosClienteDto.dataFinal());

    return ResponseEntity.ok(agendamentos.stream().map(AgendamentoDto::fromEntity).toList());
  }
}
