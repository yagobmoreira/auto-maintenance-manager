package com.ultracar.automaintenance.automaintenancemanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * The type Veiculo.
 */
@Entity
@Table(name = "veiculos")
public class Veiculo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String placa;

  @Column(nullable = false, length = 50)
  private String modelo;

  @Column(nullable = false, length = 50)
  private String marca;

  @Column(nullable = false)
  private Integer ano;

  @ManyToOne
  @JoinColumn(name = "clienteId")
  @JsonBackReference
  private Cliente cliente;

  /**
   * Instantiates a new Veiculo.
   */
  public Veiculo() {
  }

  /**
   * Instantiates a new Veiculo.
   *
   * @param placa  the placa
   * @param modelo the modelo
   * @param marca  the marca
   * @param ano    the ano
   */
  public Veiculo(String placa, String modelo, String marca, Integer ano) {
    this.placa = placa;
    this.modelo = modelo;
    this.marca = marca;
    this.ano = ano;
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
   * Gets placa.
   *
   * @return the placa
   */
  public String getPlaca() {
    return placa;
  }

  /**
   * Sets placa.
   *
   * @param placa the placa
   */
  public void setPlaca(String placa) {
    this.placa = placa;
  }

  /**
   * Gets modelo.
   *
   * @return the modelo
   */
  public String getModelo() {
    return modelo;
  }

  /**
   * Sets modelo.
   *
   * @param modelo the modelo
   */
  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  /**
   * Gets marca.
   *
   * @return the marca
   */
  public String getMarca() {
    return marca;
  }

  /**
   * Sets marca.
   *
   * @param marca the marca
   */
  public void setMarca(String marca) {
    this.marca = marca;
  }

  /**
   * Gets ano.
   *
   * @return the ano
   */
  public Integer getAno() {
    return ano;
  }

  /**
   * Sets ano.
   *
   * @param ano the ano
   */
  public void setAno(Integer ano) {
    this.ano = ano;
  }

  /**
   * Gets cliente.
   *
   * @return the cliente
   */
  public Cliente getCliente() {
    return cliente;
  }

  /**
   * Sets cliente.
   *
   * @param cliente the cliente
   */
  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Veiculo veiculo = (Veiculo) o;
    return Objects.equals(getId(), veiculo.getId()) && Objects.equals(
        getPlaca(), veiculo.getPlaca()) && Objects.equals(getModelo(),
        veiculo.getModelo()) && Objects.equals(getMarca(), veiculo.getMarca())
               && Objects.equals(getAno(), veiculo.getAno()) && Objects.equals(cliente,
        veiculo.cliente);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getPlaca(), getModelo(), getMarca(), getAno(), cliente);
  }

  @Override
  public String toString() {
    return "Veiculo{"
               + "id=" + id
               + ", placa='" + placa + '\''
               + ", modelo='" + modelo + '\''
               + ", marca='" + marca + '\''
               + ", ano=" + ano
               + ", clienteId=" + (cliente != null ? cliente.getId() : null)
               + '}';
  }
}
