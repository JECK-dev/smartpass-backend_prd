package com.smartpass.smartpassbackend.model;

import jakarta.persistence.*;


@Entity
@Table(name = "pro_categoria")

public class Categoria {

    @Id
    @Column(name = "id_categoria")
    private int idCategoria;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "detalles", nullable = false)
    private String detalles;

    @Column(name = "monto_peaje", nullable = false)
    private Double monto;


    public int getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getDetalles() {
        return detalles;
    }
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
    public Double getMonto() {
        return monto;
    }
    public void setMonto(Double monto) {
        this.monto = monto;
    }
}