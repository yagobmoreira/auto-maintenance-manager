package com.ultracar.automaintenance.automaintenancemanager.service.impl;

import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.repository.VeiculoRepository;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.VeiculoNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeiculoServiceImp {
    private final VeiculoRepository veiculoRepository;

    @Autowired
    public VeiculoServiceImp(VeiculoRepository repository) {
        this.veiculoRepository = repository;
    }

    public List<Veiculo> findAll() {
        return this.veiculoRepository.findAll();
    }

    public Veiculo findById(Long veiculoId) throws VeiculoNotFoundException {
        return this.veiculoRepository.findById(veiculoId).orElseThrow(
            VeiculoNotFoundException::new);
    }

    @Transactional
    public Veiculo create(Veiculo entity) throws BusinessException {
        if (veiculoRepository.existsByPlaca(entity.getPlaca())) {
            throw new BusinessException("Veículo já cadastrado");
        }

        return this.veiculoRepository.save(entity);
    }
}
