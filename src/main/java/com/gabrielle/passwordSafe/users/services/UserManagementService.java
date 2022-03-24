package com.gabrielle.passwordSafe.users.services;

import com.gabrielle.passwordSafe.encryption.ISecurityService;
import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.passwords.services.IPasswordManagementService;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.controllers.SubUserDTO;
import com.gabrielle.passwordSafe.users.controllers.UserCreationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@Service("userManagementService")
public class UserManagementService implements IUserManagementService {
    private static Logger logger = LoggerFactory.getLogger(UserManagementService.class);

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

    private boolean isUserRegistered(SubUserDTO userToBeSaved) {
        return userRepository.findByEmail(userToBeSaved.email) != null;
    }

    public User createSubUser(Integer adminId, SubUserDTO dto) {
        User admin = userRepository.findById(adminId);
        if(isUserRegistered(dto)) {
            return null;
        }
        User user = User.createSub(dto, admin);
        String hash = securityService.hashMasterPassword(user.getMasterPassword());
        user.setMasterPassword(hash);
        User sub = userRepository.save(user);
        return sub;
    }

    @Override
    public User findUser(Integer userId) {
        User user = userRepository.findById(userId);
        if(user.getAdmin() != null) {
            List<Password> pwds = passwordService.findUserPasswords(user.getAdmin().getId());
            user.setPasswords(new HashSet<>(pwds));
            user.setAdmin(userRepository.findById(user.getAdmin().getId()));
        }
        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
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

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
