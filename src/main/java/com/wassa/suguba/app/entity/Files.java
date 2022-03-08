package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Access(AccessType.FIELD)
public class Files extends Generality {
    private String path;
    private String type;
    private String name;
    @ManyToOne
    private Produit produit;

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
