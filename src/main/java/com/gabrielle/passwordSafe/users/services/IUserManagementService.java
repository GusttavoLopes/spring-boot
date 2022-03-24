package com.gabrielle.passwordSafe.users.services;

import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.controllers.SubUserDTO;
import com.gabrielle.passwordSafe.users.controllers.UserCreationDTO;

public interface IUserManagementService {
    User createUser(UserCreationDTO user);
    User findUser(Integer userId);
    void save(User user);
    Password addPassword(Integer userId, Password password);
    User findUserByEmail(String name);
    User createSubUser(Integer adminId, SubUserDTO dto);
}

