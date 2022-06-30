package com.wassa.suguba.app.payload;

import com.wassa.suguba.app.entity.Partenaire;
import com.wassa.suguba.authentification.entity.ApplicationUser;

import java.util.List;

public class PartenaireUsers {
    public List<ApplicationUser> users;
    public Partenaire partenaire;

    public List<ApplicationUser> getUsers() {
        return users;
    }

    public void setUsers(List<ApplicationUser> users) {
        this.users = users;
    }

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

    @Override
    public String toString() {
        return "PartenaireUsers{" +
                "users=" + users +
                ", partenaire=" + partenaire +
                '}';
    }
}
