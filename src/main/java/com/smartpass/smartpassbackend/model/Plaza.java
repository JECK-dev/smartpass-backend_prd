package com.smartpass.smartpassbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pro_plaza")
public class Plaza {

    @Id
    @Column(name = "num_plaza")
    private Integer numPlaza;

    private String nombre;

    // Opcional: relación inversa
    @OneToMany(mappedBy = "plaza")
    @JsonIgnore
    private List<Tag> tags;

    // Getters y setters
    public Integer getNumPlaza() {
        return numPlaza;
    }

    public void setNumPlaza(Integer numPlaza) {
        this.numPlaza = numPlaza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}