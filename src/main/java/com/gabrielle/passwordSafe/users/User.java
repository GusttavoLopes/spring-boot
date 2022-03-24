package com.gabrielle.passwordSafe.users;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.users.controllers.SubUserDTO;
import com.gabrielle.passwordSafe.users.controllers.UserCreationDTO;
import com.gabrielle.passwordSafe.users.controllers.UserView;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "usr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ UserView.User.class })
    private Integer id;

    @Column(name = "usr_name")
    @JsonView({ UserView.User.class })
    private String name;

    @Column(name = "usr_email")
    @JsonView({ UserView.User.class })
    private String email;

    @Column(name = "usr_master_password")
    private String masterPassword;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonView({ UserView.User.class })
    public Set<Password> passwords;

    @JsonView(UserView.User.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = {@JoinColumn(name = "usr_id")},
        inverseJoinColumns = {@JoinColumn(name = "rol_id")}
    )
    private Set<Role> roles;

    @JsonView(UserView.User.class)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="usr_admin_id")
    private User admin;

    @Column(name="usr_login_tries")
    public Integer loginTries;

    public static User create(UserCreationDTO userDTO) {
        User user = new User(userDTO.name, userDTO.email,  userDTO.masterPassword);
        user.passwords.add(userDTO.password);
        Role role = new Role(1);
        user.roles = new HashSet<Role>(){{ add(role); }};
        return user;
    }

    public static User createSub(SubUserDTO dto, User admin) {
        User user = new User(dto.name, dto.email, dto.masterPassword, admin);
        user.roles = new HashSet<Role>(){{ add(new Role(2)); }};
        return user;
    }

    public User() {}

    public User(String name, String email, String masterPassword) {
        this.name = name;
        this.email = email;
        this.masterPassword = masterPassword;
        this.passwords = new HashSet<>();
        this.loginTries = 0;
    }

    public User(String name, String email, String masterPassword, User admin) {
        this.name = name;
        this.email = email;
        this.masterPassword = masterPassword;
        this.passwords = new HashSet<>();
        this.admin = admin;
        this.loginTries = 0;
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

    public User getAdmin() { return admin; }

    public void setAdmin(User admin ) { this.admin = admin; }

    public Set<Password> getPasswords() { return this.passwords; }

    public void setPasswords(Set<Password> passwords) { this.passwords = passwords; }
}
