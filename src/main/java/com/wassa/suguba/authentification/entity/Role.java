package com.wassa.suguba.authentification.entity;

import com.wassa.suguba.app.entity.Generality;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Access(AccessType.FIELD)
public class Role extends Generality {
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
