package com.ultracar.automaintenance.automaintenancemanager.repository;

import com.ultracar.automaintenance.automaintenancemanager.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Agendamento repository.
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

}
