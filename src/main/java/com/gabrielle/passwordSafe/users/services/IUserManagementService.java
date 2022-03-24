package com.gabrielle.passwordSafe.users.services;

import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.passwords.controllers.View;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.controllers.UserCreationDTO;

public interface IUserManagementService {
    User createUser(UserCreationDTO user);
    User findUser(Integer userId);
    Password addPassword(Integer userId, Password password);

}

