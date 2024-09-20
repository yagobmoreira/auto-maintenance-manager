package com.ultracar.automaintenance.automaintenancemanager.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

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
    @MapsId
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;

    public Endereco() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Cliente getCliente() {
        return cliente;
    }

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
        return "Endereco{" +
            "id=" + id +
            ", cep='" + cep + '\'' +
            ", logradouro='" + logradouro + '\'' +
            ", complemento='" + complemento + '\'' +
            ", bairro='" + bairro + '\'' +
            ", localidade='" + localidade + '\'' +
            ", uf='" + uf + '\'' +
            ", cliente=" + cliente +
            '}';
    }
}
