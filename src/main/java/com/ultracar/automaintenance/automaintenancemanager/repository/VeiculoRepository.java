package com.ultracar.automaintenance.automaintenancemanager.repository;

import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Veiculo repository.
 */
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

  /**
   * Exists by placa boolean.
   *
   * @param placa the placa
   * @return the boolean
   */
  boolean existsByPlaca(String placa);

  /**
   * Find by placa optional.
   *
   * @param placa the placa
   * @return the optional
   */
  Optional<Veiculo> findByPlaca(String placa);
}
