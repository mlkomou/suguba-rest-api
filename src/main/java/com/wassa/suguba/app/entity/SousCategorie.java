package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "sous_categorie")
@Access(AccessType.FIELD)
public class SousCategorie extends Generality {
    private String name;
    private String path;
    @ManyToOne
    private Categorie categorie;

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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
