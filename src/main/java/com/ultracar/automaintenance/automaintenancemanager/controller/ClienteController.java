package com.ultracar.automaintenance.automaintenancemanager.controller;

import com.gtbr.ViaCepClient;
import com.gtbr.domain.Cep;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.ClienteCreationDto;
import com.ultracar.automaintenance.automaintenancemanager.controller.dto.ClienteDto;
import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.entity.Endereco;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.impl.ClienteServiceImpl;
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
@RequestMapping(value = "/clientes")
public class ClienteController {

    private final ClienteServiceImpl clienteService;

    @Autowired
    public ClienteController(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping()
    public ResponseEntity<List<ClienteDto>> findAll() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes.stream().map(ClienteDto::fromEntity).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Long id)
        throws ClienteNotFoundException {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok(ClienteDto.fromEntity(cliente));
    }

    @PostMapping
    public ResponseEntity<ClienteDto> create(@RequestBody @Valid ClienteCreationDto creationDto)
        throws BusinessException {
        Cep cep = ViaCepClient.findCep(creationDto.cep());

        if (cep == null || cep.getCep() == null) {
            throw new BusinessException("Cep inválido!");
        }
        Cliente novoCliente = creationDto.toEntity();
        Endereco endereco = new Endereco(cep.getCep(), cep.getLogradouro(), cep.getComplemento(),
            cep.getBairro(), cep.getLocalidade(), cep.getUf());
        Veiculo veiculo = new Veiculo(creationDto.placa(), creationDto.modelo(), creationDto.marca(), creationDto.ano());

        Cliente clienteCriado = clienteService.create(novoCliente, endereco, veiculo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(novoCliente.getId())
            .toUri();

        return ResponseEntity.created(location).body(ClienteDto.fromEntity(clienteCriado));
    }


}
