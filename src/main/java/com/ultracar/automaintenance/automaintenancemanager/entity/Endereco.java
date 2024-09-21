package com.ultracar.automaintenance.automaintenancemanager.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * The type Endereco.
 */
@Entity
@Table(name = "enderecos")
public class Endereco {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String cep;

  @Column(nullable = false)
  private String logradouro;

  private String complemento;
  private String bairro;
  private String localidade;
  private String uf;

  @OneToOne
  @JoinColumn(name = "cliente_id")
  @JsonBackReference
  private Cliente cliente;

  /**
   * Instantiates a new Endereco.
   */
  public Endereco() {
  }

  /**
   * Instantiates a new Endereco.
   *
   * @param cep         the cep
   * @param logradouro  the logradouro
   * @param complemento the complemento
   * @param bairro      the bairro
   * @param localidade  the localidade
   * @param uf          the uf
   */
  public Endereco(String cep, String logradouro, String complemento, String bairro,
      String localidade,
      String uf) {
    this.cep = cep;
    this.logradouro = logradouro;
    this.complemento = complemento;
    this.bairro = bairro;
    this.localidade = localidade;
    this.uf = uf;
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
   * Gets cep.
   *
   * @return the cep
   */
  public String getCep() {
    return cep;
  }

  /**
   * Sets cep.
   *
   * @param cep the cep
   */
  public void setCep(String cep) {
    this.cep = cep;
  }

  /**
   * Gets logradouro.
   *
   * @return the logradouro
   */
  public String getLogradouro() {
    return logradouro;
  }

  /**
   * Sets logradouro.
   *
   * @param logradouro the logradouro
   */
  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  /**
   * Gets complemento.
   *
   * @return the complemento
   */
  public String getComplemento() {
    return complemento;
  }

  /**
   * Sets complemento.
   *
   * @param complemento the complemento
   */
  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }

  /**
   * Gets bairro.
   *
   * @return the bairro
   */
  public String getBairro() {
    return bairro;
  }

  /**
   * Sets bairro.
   *
   * @param bairro the bairro
   */
  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  /**
   * Gets localidade.
   *
   * @return the localidade
   */
  public String getLocalidade() {
    return localidade;
  }

  /**
   * Sets localidade.
   *
   * @param localidade the localidade
   */
  public void setLocalidade(String localidade) {
    this.localidade = localidade;
  }

  /**
   * Gets uf.
   *
   * @return the uf
   */
  public String getUf() {
    return uf;
  }

  /**
   * Sets uf.
   *
   * @param uf the uf
   */
  public void setUf(String uf) {
    this.uf = uf;
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
    Endereco endereco = (Endereco) o;
    return Objects.equals(getCep(), endereco.getCep());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getCep());
  }

  @Override
  public String toString() {
    return "Endereco{"
               + "id=" + id
               + ", cep='" + cep + '\''
               + ", logradouro='" + logradouro + '\''
               + ", complemento='" + complemento + '\''
               + ", bairro='" + bairro + '\''
               + ", localidade='" + localidade + '\''
               + ", uf='" + uf + '\''
               + ", cliente=" + cliente
               + '}';
  }
}
