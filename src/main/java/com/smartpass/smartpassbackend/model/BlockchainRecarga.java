package com.smartpass.smartpassbackend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "pro_blockchain_recargas")
public class BlockchainRecarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bloque")
    private Integer idBloque;

    @Column(name = "hash_bloque")
    private String hashBloque;

    @Column(name = "hash_anterior")
    private String hashAnterior;

    @Column(name = "datos_transaccion")
    private String datosTransaccion; // JSON o texto serializado

    @Column(name = "fecha_transaccion")
    private Timestamp fechaTransaccion;

    // Getters y Setters


    public Integer getIdBloque() {
        return idBloque;
    }
    public void setIdBloque(Integer idBloque) {
        this.idBloque = idBloque;
    }
    public String getHashBloque() {
        return hashBloque;
    }
    public void setHashBloque(String hashBloque) {
        this.hashBloque = hashBloque;
    }
    public String getHashAnterior() {
        return hashAnterior;
    }
    public void setHashAnterior(String hashAnterior) {
        this.hashAnterior = hashAnterior;
    }
    public String getDatosTransaccion() {
        return datosTransaccion;
    }
    public void setDatosTransaccion(String datosTransaccion) {
        this.datosTransaccion = datosTransaccion;
    }
    public Timestamp getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Timestamp fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

}
