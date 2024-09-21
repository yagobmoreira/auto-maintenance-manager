package com.ultracar.automaintenance.automaintenancemanager.controller;

import com.ultracar.automaintenance.automaintenancemanager.controller.dto.VeiculoCreationDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.VeiculoDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.VincularVeiculoClienteDto;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
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

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController {

    private final VeiculoServiceImpl veiculoService;

    @Autowired
    public VeiculoController(VeiculoServiceImpl serviceImp) {
        this.veiculoService = serviceImp;
    }

    @GetMapping()
    public ResponseEntity<List<VeiculoDto>> findAll() {
        List<Veiculo> veiculos = veiculoService.findAll();
        return ResponseEntity.ok(veiculos.stream().map(VeiculoDto::fromEntity).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDto> findById(@PathVariable Long id)
        throws VeiculoNotFoundException {
        Veiculo veiculo = veiculoService.findById(id);

        return ResponseEntity.ok(VeiculoDto.fromEntity(veiculo));
    }

    @PostMapping
    public ResponseEntity<VeiculoDto> create(@RequestBody @Valid VeiculoCreationDto creationDto)
        throws BusinessException {
        Veiculo novoVeiculo = this.veiculoService.create(creationDto.toEntity());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(novoVeiculo.getId())
            .toUri();

        return ResponseEntity.created(location).body(VeiculoDto.fromEntity(novoVeiculo));
    }

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<VeiculoDto> setVeiculoCliente(
        @RequestBody @Valid VincularVeiculoClienteDto vincularDto, @PathVariable Long clienteId)
        throws BusinessException, ClienteNotFoundException, VeiculoNotFoundException {
        Veiculo novoVeiculo =  this.veiculoService.addVeiculoToCliente(vincularDto.placa(), clienteId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(novoVeiculo.getId())
            .toUri();

        return ResponseEntity.created(location).body(VeiculoDto.fromEntity(novoVeiculo));
    }

}
