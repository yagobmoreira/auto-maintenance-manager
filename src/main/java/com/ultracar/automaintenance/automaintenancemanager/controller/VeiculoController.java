package com.ultracar.automaintenance.automaintenancemanager.controller;

import com.ultracar.automaintenance.automaintenancemanager.controller.dto.VeiculoCreationDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.VeiculoDto;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.VeiculoNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.impl.VeiculoServiceImpl;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * The type Veiculo controller.
 */
@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController {

  private final VeiculoServiceImpl veiculoService;

  /**
   * Instantiates a new Veiculo controller.
   *
   * @param serviceImp the service imp
   */
  @Autowired
  public VeiculoController(VeiculoServiceImpl serviceImp) {
    this.veiculoService = serviceImp;
  }

  /**
   * Listar todos os veículos.
   *
   * @return DTO de todos os veículos
   */
  @GetMapping()
  @Operation(summary = "Buscar veículos", description = "Listar todos os veículos")
  @ApiResponse(responseCode = "200", description = "Retorno de todos os veículos",
      content = @Content(array = @ArraySchema(
          schema = @Schema(implementation = VeiculoDto.class)
      )))
  public ResponseEntity<List<VeiculoDto>> obterVeiculos() {
    List<Veiculo> veiculos = veiculoService.obterVeiculos();
    return ResponseEntity.ok(veiculos.stream().map(VeiculoDto::fromEntity).toList());
  }

  /**
   * Lista um veículo pelo 'ID'.
   *
   * @param id 'ID' do veículo
   * @return DTO do veículo
   * @throws VeiculoNotFoundException Caso o veículo não seja encontrado
   */
  @GetMapping("/{id}")
  @Operation(summary = "Buscar um veículo", description = "Buscar um veículo pelo 'ID'")
  @ApiResponse(responseCode = "200", description = "Retorno de um veículo",
      content = @Content(schema = @Schema(implementation = VeiculoDto.class)))
  @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
  public ResponseEntity<VeiculoDto> obterVeiculoPeloId(@PathVariable Long id)
      throws VeiculoNotFoundException {
    Veiculo veiculo = veiculoService.obterVeiculoPeloId(id);

    return ResponseEntity.ok(VeiculoDto.fromEntity(veiculo));
  }

  /**
   * Criar um veículo.
   *
   * @param creationDto DTO de criação de veículo
   * @return DTO do veículo criado
   * @throws BusinessException Caso o veículo já tenha sido cadastrado.
   */
  @PostMapping
  @Operation(summary = "Criar um veículo", description = "Criar um veículo")
  @ApiResponse(responseCode = "201", description = "Veículo criado",
      content = @Content(schema = @Schema(implementation = VeiculoDto.class)))
  @ApiResponse(responseCode = "400", description = "Veículo já cadastrado")
  public ResponseEntity<VeiculoDto> criarVeiculo(@RequestBody @Valid VeiculoCreationDto creationDto)
      throws BusinessException {
    Veiculo novoVeiculo = this.veiculoService.criarVeiculo(creationDto.toEntity());

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(novoVeiculo.getId())
                       .toUri();

    return ResponseEntity.created(location).body(VeiculoDto.fromEntity(novoVeiculo));
  }

}
