package com.gabrielle.passwordSafe.users.services;

import com.gabrielle.passwordSafe.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findById(Integer id);
    User findByEmail(String email);
}
