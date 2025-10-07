package com.smartpass.smartpassbackend.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "pro_transacciones_saldo")
public class TransaccionSaldo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @Column(name = "id_contrato", nullable = false)
    private Integer idContrato;

    @Column(name = "tipo_transaccion", nullable = false)
    private String tipoTransaccion;

    @Column(name = "monto_anterior", nullable = false)
    private BigDecimal montoAnterior;

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @Column(name = "saldo_final", nullable = false)
    private BigDecimal saldoFinal;

    @Column(name = "fecha")
    private Timestamp fecha;

    @Column(name = "id_recarga")
    private Integer idRecarga;

    @Column(name = "id_transito")
    private Integer idTransito;

    @Column(name = "descripcion")
    private String descripcion;

    // Getters y Setters

    public Integer getIdContrato() {
        return idContrato;
    }
    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }
    public String getTipoTransaccion() {
        return tipoTransaccion;
    }
    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    public BigDecimal getMontoAnterior() {
        return montoAnterior;
    }
    public void setMontoAnterior(BigDecimal montoAnterior) {
        this.montoAnterior = montoAnterior;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
    public Integer getIdRecarga() {
        return idRecarga;
    }
    public void setIdRecarga(Integer idRecarga) {
        this.idRecarga = idRecarga;
    }
    public Integer getIdTransito() {
        return idTransito;
    }
    public void setIdTransito(Integer idTransito) {
        this.idTransito = idTransito;
    }
    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    public Timestamp getFecha() {
        return fecha;
    }
}

