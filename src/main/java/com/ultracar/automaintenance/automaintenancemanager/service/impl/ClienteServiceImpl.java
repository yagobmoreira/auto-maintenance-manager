package com.ultracar.automaintenance.automaintenancemanager.service.impl;

import static java.util.Optional.ofNullable;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.repository.ClienteRepository;
import com.ultracar.automaintenance.automaintenancemanager.service.ClienteService;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository repository) {
        this.clienteRepository = repository;
    }

    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    public Cliente findById(Long clienteId) throws ClienteNotFoundException {
        Cliente dbClient = this.clienteRepository.findById(clienteId).orElseThrow(ClienteNotFoundException::new);

        if (dbClient.isDeleted()) {
            throw new ClienteNotFoundException();
        }

        return dbClient;
    }

    @Transactional
    public Cliente create(Cliente entity) throws BusinessException {
       if (clienteRepository.existsClienteByCpf(entity.getCpf())) {
           throw new BusinessException("CPF já cadastrado anteriormente");
       }

       return this.clienteRepository.save(entity);
    }

    @Transactional
    public Cliente update(Long clienteId, Cliente entity)
        throws BusinessException, ClienteNotFoundException {
        Cliente dbCliente = this.findById(clienteId);

        if (!dbCliente.getId().equals(entity.getId())) {
            throw new BusinessException("O id deve ser o mesmo");
        }

        dbCliente.setCpf(entity.getCpf());
        dbCliente.setNome(entity.getNome());

        return this.clienteRepository.save(dbCliente);
    }

    @Transactional
    public void delete(Long clienteId) throws ClienteNotFoundException {
        Cliente dbCliente = this.findById(clienteId);
        dbCliente.setDeleted();

        this.clienteRepository.save(dbCliente);
    }
}
