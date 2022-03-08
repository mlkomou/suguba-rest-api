package com.wassa.suguba.authentification.entity;

import com.wassa.suguba.app.entity.Generality;

import javax.persistence.*;

@Entity
@Table(name = "role_user")
@Access(AccessType.FIELD)
public class RoleUser extends Generality {

    @ManyToOne
    private ApplicationUser applicationUser;

    @ManyToOne
    private Role role;

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
