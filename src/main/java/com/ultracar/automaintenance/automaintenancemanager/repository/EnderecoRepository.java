package com.ultracar.automaintenance.automaintenancemanager.repository;

import com.ultracar.automaintenance.automaintenancemanager.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Endereco repository.
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
