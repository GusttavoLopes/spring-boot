package com.gabrielle.passwordSafe.users.services;

import com.gabrielle.passwordSafe.users.User;

public interface IUserManagementService {
    Integer createUser(User user);
    User findUser(Integer userId);
}

