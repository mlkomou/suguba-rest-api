package com.wassa.suguba.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "compteur")
public class Compteur extends Generality {
    @Column(name = "nombre", nullable = false)
    public int nombre;

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
