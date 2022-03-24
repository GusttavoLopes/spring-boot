package com.gabrielle.passwordSafe.users;

import com.fasterxml.jackson.annotation.JsonView;
import com.gabrielle.passwordSafe.users.controllers.View;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    public Role() {}

    public Role(Integer id) {
        this.id = id;
    }

    @Id
    @Column(name = "rol_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView({ View.Role.class, View.User.class })
    @Column(name = "rol_name")
    private String name;

    @JsonView({ View.Role.class, View.User.class })
    @Column(name = "rol_description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsers() {
        return users;
    }

}
