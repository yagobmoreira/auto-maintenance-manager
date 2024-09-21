package com.ultracar.automaintenance.automaintenancemanager.controller;

import com.ultracar.automaintenance.automaintenancemanager.controller.dto.VeiculoCreationDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.VeiculoDto;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.VeiculoNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.impl.VeiculoServiceImpl;
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
   * Find all response entity.
   *
   * @return the response entity
   */
  @GetMapping()
  public ResponseEntity<List<VeiculoDto>> findAll() {
    List<Veiculo> veiculos = veiculoService.findAll();
    return ResponseEntity.ok(veiculos.stream().map(VeiculoDto::fromEntity).toList());
  }

  /**
   * Find by id response entity.
   *
   * @param id the id
   * @return the response entity
   * @throws VeiculoNotFoundException the veiculo not found exception
   */
  @GetMapping("/{id}")
  public ResponseEntity<VeiculoDto> findById(@PathVariable Long id)
      throws VeiculoNotFoundException {
    Veiculo veiculo = veiculoService.findById(id);

    return ResponseEntity.ok(VeiculoDto.fromEntity(veiculo));
  }

  /**
   * Create response entity.
   *
   * @param creationDto the creation dto
   * @return the response entity
   * @throws BusinessException the business exception
   */
  @PostMapping
  public ResponseEntity<VeiculoDto> create(@RequestBody @Valid VeiculoCreationDto creationDto)
      throws BusinessException {
    Veiculo novoVeiculo = this.veiculoService.criarVeiculo(creationDto.toEntity());

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(novoVeiculo.getId())
                       .toUri();

    return ResponseEntity.created(location).body(VeiculoDto.fromEntity(novoVeiculo));
  }

}
