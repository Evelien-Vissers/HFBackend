package com.novi.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")

public class Role extends BaseEntity {

    @Column(nullable = false)
    private String roleName;

    @Column(nullable = false)
    private boolean active;

    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    //Constructors
    public Role() {
        super();
    }

    public Role(String roleName) {
        this.roleName = roleName;
        this.active = true;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    //Voeg een gebruiker toe aan deze rol
    public void addUser(User user) {
        this.users.add(user);
        user.getRoles().add(this);
    }
}
