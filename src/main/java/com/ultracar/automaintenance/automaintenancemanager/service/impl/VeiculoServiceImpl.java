package com.ultracar.automaintenance.automaintenancemanager.service.impl;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.repository.ClienteRepository;
import com.ultracar.automaintenance.automaintenancemanager.repository.VeiculoRepository;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.VeiculoNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeiculoServiceImpl {

    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public VeiculoServiceImpl(VeiculoRepository veiculoRepository, ClienteRepository clienteRepository) {
        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Veiculo> findAll() {
        return this.veiculoRepository.findAll();
    }

    public Veiculo findById(Long veiculoId) throws VeiculoNotFoundException {
        return this.veiculoRepository.findById(veiculoId).orElseThrow(
            VeiculoNotFoundException::new);
    }

    @Transactional
    public Veiculo create(Veiculo novoVeiculo) throws BusinessException {
        checkIfPlacaExists(novoVeiculo.getPlaca());

        return this.veiculoRepository.save(novoVeiculo);
    }

    @Transactional
    public Veiculo addVeiculoToCliente(Veiculo novoVeiculo, Long clienteId)
        throws ClienteNotFoundException, BusinessException {

        checkIfPlacaExists(novoVeiculo.getPlaca());

        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(ClienteNotFoundException::new);

        novoVeiculo.setCliente(cliente);
        return veiculoRepository.save(novoVeiculo);
    }

    private void checkIfPlacaExists(String placa) throws BusinessException {
        if (veiculoRepository.existsByPlaca(placa)) {
            throw new BusinessException("Veículo já cadastrado com a placa: " + placa);
        }
    }
}
