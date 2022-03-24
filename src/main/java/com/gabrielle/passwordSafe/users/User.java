package com.gabrielle.passwordSafe.users;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.users.controllers.UserCreationDTO;
import com.gabrielle.passwordSafe.users.controllers.View;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "usr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ View.User.class })
    private Integer id;

    @Column(name = "usr_name")
    @JsonView({ View.User.class })
    private String name;

    @Column(name = "usr_email")
    @JsonView({ View.User.class })
    private String email;

    @Column(name = "usr_master_password")
    @JsonView(View.User.class)
    private String masterPassword;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Password> passwords;

    @JsonView(View.User.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = {@JoinColumn(name = "rol_id")},
        inverseJoinColumns = {@JoinColumn(name = "usr_id")}
    )
    private Set<Role> roles;

    public static User create(UserCreationDTO userDTO) {
        User user = new User(userDTO.name, userDTO.email,  userDTO.masterPassword);
        user.passwords.add(userDTO.password);
        user.roles = new HashSet<Role>(){{ add(new Role(2)); }};
        return user;
    }

    public User() {}

    public User(String name, String email, String masterPassword) {
        this.name = name;
        this.email = email;
        this.masterPassword = masterPassword;
        this.passwords = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String[] getRoleNames() {
        return roles
            .stream()
            .map(Role::getName)
            .collect(Collectors.toList())
            .toArray(new String[roles.size()]);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
