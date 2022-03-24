package com.gabrielle.passwordSafe.users.services;

import com.gabrielle.passwordSafe.encryption.ISecurityService;
import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.passwords.services.IPasswordManagementService;
import com.gabrielle.passwordSafe.users.Role;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.controllers.UserCreationDTO;
import com.gabrielle.passwordSafe.users.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service("userManagementService")
public class UserManagementService implements IUserManagementService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    ISecurityService securityService;

    @Autowired
    IPasswordManagementService passwordService;

    @Transactional
    @Override
    public User createUser(UserCreationDTO userDTO) {
        boolean userExists = isUserRegistered(userDTO);
        if(userExists || !userDTO.isValid()) {
            return null;
        }

        User user = User.create(userDTO);

        String hash = securityService.hashMasterPassword(user.getMasterPassword());
        user.setMasterPassword(hash);

        user = userRepository.save(user);
        userDTO.password.setUser(user);
        Integer created = passwordService.createPassword(userDTO.password);
        if (created > 0) {
            return user;
        }
        return null;
    }

    private boolean isUserRegistered(UserCreationDTO userToBeSaved) {
        return userRepository.findByEmail(userToBeSaved.email) != null;
    }


    @Override
    public User findUser(@PathVariable Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public Password addPassword(Integer userId, Password password) {
        User user = userRepository.findById(userId);
        if(user != null) {
            password.setUser(user);
            passwordService.createPassword(password);
        }
        return password;
    }
}
