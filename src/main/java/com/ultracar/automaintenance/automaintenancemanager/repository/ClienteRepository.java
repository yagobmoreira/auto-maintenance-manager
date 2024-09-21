package com.ultracar.automaintenance.automaintenancemanager.repository;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Cliente repository.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  /**
   * Exists cliente by cpf boolean.
   *
   * @param cpf the cpf
   * @return the boolean
   */
  boolean existsClienteByCpf(String cpf);
}
