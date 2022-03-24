package com.gabrielle.passwordSafe.users.controllers;

import com.gabrielle.passwordSafe.users.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UserDTO {
    public final String name;
    public final String email;
    public final String master_password;

    private UserDTO(String name, String email, String master_password) {
        this.name = name;
        this.email = email;
        this.master_password = master_password;
    }

    @Contract("_ -> new")
    public static @NotNull UserDTO create(User user) {
        return new UserDTO(user.getName(), user.getEmail(), user.getMasterPassword());
    }
}
