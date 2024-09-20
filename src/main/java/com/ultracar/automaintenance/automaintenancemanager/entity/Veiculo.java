package com.ultracar.automaintenance.automaintenancemanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

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
    private Cliente cliente;

    public Veiculo() {
    }

    public Veiculo(String placa, String modelo, String marca, Integer ano) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
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
        return "Veiculo{" +
            "id=" + id +
            ", placa='" + placa + '\'' +
            ", modelo='" + modelo + '\'' +
            ", marca='" + marca + '\'' +
            ", ano=" + ano +
            ", cliente=" + cliente +
            '}';
    }
}
