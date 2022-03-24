package com.gabrielle.passwordSafe.passwords.controllers;

import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.controllers.UserDTO;

public class PasswordDTO {
    public final String name;
    public final String password;
	
    private PasswordDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public static PasswordDTO create(Password password) {
        return new PasswordDTO(password.getName(), password.getPassword());
    }

}
