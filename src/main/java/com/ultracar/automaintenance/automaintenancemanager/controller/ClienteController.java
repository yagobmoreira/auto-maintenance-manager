package com.ultracar.automaintenance.automaintenancemanager.controller;

import com.gtbr.ViaCepClient;
import com.gtbr.domain.Cep;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.ClienteCreationDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.ClienteDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.VincularVeiculoClienteDto;
import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.entity.Endereco;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.VeiculoNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.impl.ClienteServiceImpl;
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
 * The type Cliente controller.
 */
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

  private final ClienteServiceImpl clienteService;

  /**
   * Listar todos os clientes.
   */
  @Autowired
  public ClienteController(ClienteServiceImpl clienteService) {
    this.clienteService = clienteService;
  }

  /**
   * Find all response entity.
   *
   * @return the response entity
   */
  @GetMapping()
  @Operation(summary = "Buscar clientes", description = "Listar todos os clientes")
  @ApiResponse(responseCode = "200", description = "Retorno de todos os clientes",
      content = @Content(array = @ArraySchema(
          schema = @Schema(implementation = ClienteDto.class)
      )))
  public ResponseEntity<List<ClienteDto>> findAll() {
    List<Cliente> clientes = clienteService.findAll();
    return ResponseEntity.ok(clientes.stream().map(ClienteDto::fromEntity).toList());
  }

  /**
   * Lista um cliente pelo 'ID'.
   *
   * @param id 'ID' do cliente
   * @return DTO do cliente
   * @throws ClienteNotFoundException Caso não encontre o cliente
   */
  @GetMapping("/{id}")
  @Operation(summary = "Buscar um cliente", description = "Buscar um cliente pelo 'ID'")
  @ApiResponse(responseCode = "200", description = "Retorno do cliente",
      content = @Content(schema = @Schema(implementation = ClienteDto.class)))
  @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
  public ResponseEntity<ClienteDto> findById(@PathVariable Long id)
      throws ClienteNotFoundException {
    Cliente cliente = clienteService.findById(id);
    return ResponseEntity.ok(ClienteDto.fromEntity(cliente));
  }

  /**
   * Criar um cliente.
   *
   * @param creationDto DTO de criação de cliente
   * @return DTO do cliente criado
   * @throws BusinessException Caso cep inválido, cpf já cadastrado ou placa já cadastrada
   */
  @PostMapping
  @Operation(summary = "Criar um cliente", description = "Criar um cliente")
  @ApiResponse(responseCode = "201", description = "Cliente criado",
      content = @Content(schema = @Schema(implementation = ClienteDto.class)))
  @ApiResponse(responseCode = "400", description = "Cep inválido")
  @ApiResponse(responseCode = "400", description = "Cpf já cadastrado")
  @ApiResponse(responseCode = "400", description = "Placa já cadastrada")
  public ResponseEntity<ClienteDto> create(@RequestBody @Valid ClienteCreationDto creationDto)
      throws BusinessException {
    Cep cep = ViaCepClient.findCep(creationDto.cep());

    if (cep == null || cep.getCep() == null) {
      throw new BusinessException("Cep inválido!");
    }
    Cliente novoCliente = creationDto.toEntity();
    Endereco endereco = new Endereco(cep.getCep(), cep.getLogradouro(), cep.getComplemento(),
        cep.getBairro(), cep.getLocalidade(), cep.getUf());
    Veiculo veiculo = new Veiculo(creationDto.placa(), creationDto.modelo(),
        creationDto.marca(), creationDto.ano());

    Cliente clienteCriado = clienteService.create(novoCliente, endereco, veiculo);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(novoCliente.getId())
                       .toUri();

    return ResponseEntity.created(location).body(ClienteDto.fromEntity(clienteCriado));
  }

  /**
   * Vincular um veiculo a um cliente.
   *
   * @param clienteDto DTO de vinculação de veiculo
   * @param clienteId  'ID' do cliente
   * @return DTO do cliente atualizado
   * @throws ClienteNotFoundException Caso não encontre o cliente
   * @throws VeiculoNotFoundException Caso não encontre o veículo
   */
  @PatchMapping("/{clienteId}/veiculos")
  @Operation(summary = "Vincular veículo a um cliente",
      description = "Vincular um veículo a um cliente")
  @ApiResponse(responseCode = "200", description = "Veículo vinculado",
      content = @Content(schema = @Schema(implementation = ClienteDto.class)))
  @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
  @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
  public ResponseEntity<ClienteDto> vincularVeiculo(
      @RequestBody @Valid VincularVeiculoClienteDto clienteDto, @PathVariable Long clienteId)
      throws ClienteNotFoundException, VeiculoNotFoundException {

    Cliente clienteAtualizado = this.clienteService.adicionarVeiculo(clienteDto.placa(),
        clienteId);

    return ResponseEntity.ok(ClienteDto.fromEntity(clienteAtualizado));
  }
}
