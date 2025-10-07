package com.smartpass.smartpassbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pro_tipo_facturacion")
public class TipoFacturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoFacturacion;

    private String descripcion;

    public TipoFacturacion() {}

    public TipoFacturacion(Integer id) {
        this.idTipoFacturacion = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public Integer getIdTipoFacturacion() {
        return idTipoFacturacion;
    }
    public void setIdTipoFacturacion(Integer idTipoFacturacion) {
        this.idTipoFacturacion = idTipoFacturacion;
    }

}
