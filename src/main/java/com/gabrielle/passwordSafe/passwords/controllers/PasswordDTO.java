package com.gabrielle.passwordSafe.passwords.controllers;

import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.controllers.UserDTO;

public class PasswordDTO {
    public final String name;
    public final String password;
    public final User user;
	
    private PasswordDTO(String name,String  password, User user) {
        this.name = name;
        this.password = password;
        this.user = user;
    }
    
    public static PasswordDTO create(Password password) {
        return new PasswordDTO(password.getName(), password.getPassword(), password.getUser());
    }

}
