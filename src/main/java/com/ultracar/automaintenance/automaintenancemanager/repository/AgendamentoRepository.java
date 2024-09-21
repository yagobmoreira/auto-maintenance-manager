package com.ultracar.automaintenance.automaintenancemanager.repository;

import com.ultracar.automaintenance.automaintenancemanager.entity.Agendamento;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface Agendamento repository.
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

  @Query("SELECT a FROM  Agendamento a WHERE a.cliente.id = :clienteId "
             + "AND a.dataAgendamento BETWEEN :dataInicial AND :dataFinal")
  List<Agendamento> findByClienteIdBetweenDates(
      @Param("clienteId") Long clienteId,
      @Param("dataInicial") LocalDateTime startDate,
      @Param("dataFinal") LocalDateTime endDate);
}
