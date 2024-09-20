package com.ultracar.automaintenance.automaintenancemanager.service;

import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface CrudService<ID, T> {
    List<T> findAll();
    T findById(ID id) throws NotFoundException, ClienteNotFoundException;
    T create(T entity) throws BusinessException;
    T update(ID id, T entity) throws NotFoundException, BusinessException, ClienteNotFoundException;
    void delete(ID id) throws NotFoundException, ClienteNotFoundException;
}

