package com.wassa.suguba.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
@Access(AccessType.FIELD)
public class Produit extends Generality{
    @ManyToOne
    Categorie categorie;
    String name;
    @Lob
    String description;
    String descriptionPath;
    String reference;
    Double prix;
    Integer quantiteInitiale;
    Integer quantiteVendue;
    @OneToMany(mappedBy = "produit")
    @JsonIgnoreProperties(value = {"produit"}, allowSetters = true)
    private List<Files> files;

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    public String getDescriptionPath() {
        return descriptionPath;
    }

    public void setDescriptionPath(String descriptionPath) {
        this.descriptionPath = descriptionPath;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getQuantiteInitiale() {
        return quantiteInitiale;
    }

    public void setQuantiteInitiale(Integer quantiteInitiale) {
        this.quantiteInitiale = quantiteInitiale;
    }

    public Integer getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(Integer quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

}
