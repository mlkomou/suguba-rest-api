package com.wassa.suguba.app.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "partenaire")
@Access(AccessType.FIELD)
public class Partenaire extends Generality{
    private String nom;
    private String description;
}
