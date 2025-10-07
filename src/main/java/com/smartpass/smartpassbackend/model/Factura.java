package com.smartpass.smartpassbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pro_facturacion")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facturacion")
    private Integer idfacturacion;

    @Column(name = "num_docum")
    private String numeroDocumento;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "serie")
    private Integer serie;

    @Column(name = "correlativo")
    private String correlativo;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "igv")
    private BigDecimal igv;

    @Column(name = "fecha_comprobante")
    private LocalDateTime fechaComprobante;

    @Column(name = "fecha_cruce_peaje")
    private LocalDateTime fechaCrucePeaje;

    @Column(name = "id_transito", nullable = false)
    private Integer idTransito;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "id_tipo_facturacion")
    private Integer tipoFacturacion; // 1=Prepago, 2=Pospago

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetalleFactura> detalles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_contrato")
    private Contrato contrato;

    public String getNumeroDocumento() {
        return numeroDocumento;
    }
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getSerie() {
        return serie;
    }
    public void setSerie(Integer serie) {
        this.serie = serie;
    }
    public String getCorrelativo() {
        return correlativo;
    }
    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public BigDecimal getIgv() {
        return igv;
    }
    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }
    public LocalDateTime getFechaComprobante() {
        return fechaComprobante;
    }
    public void setFechaComprobante(LocalDateTime fechaComprobante) {
        this.fechaComprobante = fechaComprobante;
    }
    public LocalDateTime getFechaCrucePeaje() {
        return fechaCrucePeaje;
    }

    public void setFechaCrucePeaje(LocalDateTime fechaCrucePeaje) {
        this.fechaCrucePeaje = fechaCrucePeaje;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Integer getTipoFacturacion() {
        return tipoFacturacion;
    }

    public Integer getIdfacturacion() {
        return idfacturacion;
    }


    public void setTipoFacturacion(Integer tipoFacturacion) {
        this.tipoFacturacion = tipoFacturacion;
    }
    public List<DetalleFactura> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }

    public void setIdfacturacion(Integer idfacturacion) {
        this.idfacturacion = idfacturacion;
    }

    public Contrato getContrato() {
        return contrato;
    }
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Integer getIdTransito() {
        return idTransito;
    }
    public void setIdTransito(Integer idTransito) {
        this.idTransito = idTransito;
    }

}

