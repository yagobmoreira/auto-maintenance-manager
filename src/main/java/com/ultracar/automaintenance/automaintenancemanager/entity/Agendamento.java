package com.ultracar.automaintenance.automaintenancemanager.entity;

import com.ultracar.automaintenance.automaintenancemanager.enums.StatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    private LocalDateTime dataAgendamento;

    private String descricaoServico;

    private StatusType status;

    public Agendamento() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Agendamento that = (Agendamento) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(
            getClienteId(), that.getClienteId()) && Objects.equals(getDataAgendamento(),
            that.getDataAgendamento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClienteId(), getDataAgendamento());
    }

    @Override
    public String toString() {
        return "Agendamento{" +
            "id=" + id +
            ", clienteId=" + clienteId +
            ", dataAgendamento=" + dataAgendamento +
            ", descricaoServico='" + descricaoServico + '\'' +
            ", status=" + status +
            '}';
    }
}
