package com.gabrielle.passwordSafe.users;


import java.util.Set;

import javax.persistence.*;

import com.gabrielle.passwordSafe.passwords.Password;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "usr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usr_name")
    private String name;

    @Column(name = "usr_email")
    private String email;

    @Column(name = "usr_master_password")
    private String masterPassword;
    
    public User() {}

    public User(String name,String  email,String  masterPassword) {
        this.name = name;
        this.email = email;
        this.masterPassword = masterPassword;
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

    public boolean isValid() {
        return name != null && email != null && masterPassword != null;
    }
}
