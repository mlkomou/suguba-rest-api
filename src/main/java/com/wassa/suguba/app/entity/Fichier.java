package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "fichier")
@Access(AccessType.FIELD)
public class Fichier extends Generality{
    private String path;
    private String name;
    private Integer size;
    @ManyToOne
    Souscrition souscrition;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Souscrition getSouscrition() {
        return souscrition;
    }

    public void setSouscrition(Souscrition souscrition) {
        this.souscrition = souscrition;
    }
}
