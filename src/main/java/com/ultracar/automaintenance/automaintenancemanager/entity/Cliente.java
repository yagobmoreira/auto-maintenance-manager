package com.ultracar.automaintenance.automaintenancemanager.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The type Cliente.
 */
@Entity
@Table(name = "clientes")
@EntityListeners(AuditingEntityListener.class)
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(unique = true, nullable = false)
  private String cpf;

  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Veiculo> veiculos;

  @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "endereco_id")
  @JsonManagedReference
  private Endereco endereco;

  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Agendamento> agendamentos;

  private boolean deleted;

  @CreatedDate
  @JsonProperty("created_date")
  private LocalDate createDate;

  @LastModifiedDate
  @JsonProperty("last_modified_date")
  private LocalDate lastModifiedDate;

  /**
   * Instantiates a new Cliente.
   */
  public Cliente() {
  }

  /**
   * Instantiates a new Cliente.
   *
   * @param nome the nome
   * @param cpf  the cpf
   */
  public Cliente(String nome, String cpf) {
    this.nome = nome;
    this.cpf = cpf;
    this.veiculos = new ArrayList<>();
    this.agendamentos = new ArrayList<>();
    this.deleted = false;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets nome.
   *
   * @return the nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * Sets nome.
   *
   * @param nome the nome
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * Gets cpf.
   *
   * @return the cpf
   */
  public String getCpf() {
    return cpf;
  }

  /**
   * Sets cpf.
   *
   * @param cpf the cpf
   */
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  /**
   * Gets veiculos.
   *
   * @return the veiculos
   */
  public List<Veiculo> getVeiculos() {
    return veiculos;
  }

  /**
   * Sets veiculo.
   *
   * @param veiculo the veiculo
   */
  public void setVeiculo(
      Veiculo veiculo) {
    this.veiculos.add(veiculo);
  }

  /**
   * Gets endereco.
   *
   * @return the endereco
   */
  public Endereco getEndereco() {
    return endereco;
  }

  /**
   * Sets endereco.
   *
   * @param endereco the endereco
   */
  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }

  /**
   * Gets agendamentos.
   *
   * @return the agendamentos
   */
  public List<Agendamento> getAgendamentos() {
    return agendamentos;
  }

  /**
   * Sets agendamento.
   *
   * @param agendamento the agendamento
   */
  public void setAgendamento(
      Agendamento agendamento) {
    this.agendamentos.add(agendamento);
  }

  /**
   * Is deleted boolean.
   *
   * @return the boolean
   */
  public boolean isDeleted() {
    return deleted;
  }

  /**
   * Sets deleted.
   */
  public void setDeleted() {
    this.deleted = true;
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
    Cliente cliente = (Cliente) o;
    return Objects.equals(getId(), cliente.getId()) && Objects.equals(getCpf(),
        cliente.getCpf());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getCpf());
  }

  @Override
  public String toString() {
    return "Cliente{"
               + "id=" + id
               + ", nome='" + nome + '\''
               + ", cpf='" + cpf + '\''
               + ", veiculos=" + (veiculos != null ? veiculos : "nenhum")
               + '}';
  }
}
