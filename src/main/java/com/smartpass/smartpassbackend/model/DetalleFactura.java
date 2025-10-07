package com.smartpass.smartpassbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pro_detalle_factura")
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_facturacion")
    @JsonIgnore
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "id_transito")
    private Transito transito;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "fecha_cruce")
    private LocalDateTime fechaCruce;

    public Integer getIdDetalle() {
        return idDetalle;
    }
    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }
    public Factura getFactura() {
        return factura;
    }
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    public Transito getTransito() {
        return transito;
    }
    public void setTransito(Transito transito) {
        this.transito = transito;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public LocalDateTime getFechaCruce() {
        return fechaCruce;
    }
    public void setFechaCruce(LocalDateTime fechaCruce) {
        this.fechaCruce = fechaCruce;
    }

}