package com.gabrielle.passwordSafe.users.services;

import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.repositories.IUserRepository;
import com.gabrielle.passwordSafe.users.services.IUserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.SystemException;
import javax.transaction.Transactional;

@Service("userManagementService")
public class UserManagementService implements IUserManagementService {
    @Autowired
    IUserRepository userRepository;

    @Transactional
    @Override
    public Integer createUser(User user) {
        boolean userExists = isUserRegistered(user);
        if(userExists || !user.isValid()) {
            return -1;
        }
        user = userRepository.save(user);
        return user.getId();
    }

    private boolean isUserRegistered(User userToBeSaved) {
        return userRepository.findByEmail(userToBeSaved.getEmail()) != null;
    }

    @Override
    public User findUser(@PathVariable Integer userId) {
        return userRepository.findById(userId);
    }
}
