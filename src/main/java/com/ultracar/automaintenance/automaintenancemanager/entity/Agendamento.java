package com.ultracar.automaintenance.automaintenancemanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ultracar.automaintenance.automaintenancemanager.enums.StatusType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The type Agendamento.
 */
@Entity
@Table(name = "agendamentos")
@EntityListeners(AuditingEntityListener.class)
public class Agendamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "clienteId", nullable = false)
  @JsonBackReference
  private Cliente cliente;

  private LocalDateTime dataAgendamento;

  private String descricaoServico;

  @Enumerated(EnumType.STRING)
  private StatusType status;

  @CreatedDate
  @JsonProperty("created_date")
  private LocalDate createDate;

  @LastModifiedDate
  @JsonProperty("last_modified_date")
  private LocalDate lastModifiedDate;

  public Agendamento() {
  }

  /**
   * Instantiates a new Agendamento.
   *
   * @param dataAgendamento  the data agendamento
   * @param descricaoServico the descricao servico
   */
  public Agendamento(LocalDateTime dataAgendamento, String descricaoServico) {
    this.dataAgendamento = dataAgendamento;
    this.descricaoServico = descricaoServico;
    this.status = StatusType.PENDENTE;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public LocalDateTime getDataAgendamento() {
    return dataAgendamento;
  }

  public void setDataAgendamento(LocalDateTime dataAgendamento) {
    this.dataAgendamento = dataAgendamento;
  }

  public String getDescricaoServico() {
    return descricaoServico;
  }

  public void setDescricaoServico(String descricaoServico) {
    this.descricaoServico = descricaoServico;
  }

  public StatusType getStatus() {
    return status;
  }

  public void setStatus(StatusType status) {
    this.status = status;
  }

  public LocalDate getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDate createDate) {
    this.createDate = createDate;
  }

  public LocalDate getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDate lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Agendamento that = (Agendamento) o;
    return Objects.equals(getId(), that.getId()) && Objects.equals(getCliente(), that.getCliente())
               && Objects.equals(getDataAgendamento(), that.getDataAgendamento());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getCliente(), getDataAgendamento());
  }

  @Override
  public String toString() {
    return "Agendamento{" + "id=" + id + ", cliente=" + cliente + ", dataAgendamento="
               + dataAgendamento + ", descricaoServico='" + descricaoServico + '\'' + ", status="
               + status
               + '}';
  }
}
