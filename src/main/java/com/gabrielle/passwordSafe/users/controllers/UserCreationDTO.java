package com.gabrielle.passwordSafe.users.controllers;

import com.gabrielle.passwordSafe.passwords.Password;

public class UserCreationDTO {
    public final String name;
    public final String email;
    public final String masterPassword;
    public final Password password;

    public UserCreationDTO(String name, String email, String masterPassword, Password passwordDTO) {
        this.name = name;
        this.email = email;
        this.masterPassword = masterPassword;
        this.password = passwordDTO;
    }

    public boolean isValid() {
        return name != null && email != null && masterPassword != null && password != null;
    }
}
