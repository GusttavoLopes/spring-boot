package com.gabrielle.passwordSafe.passwords.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielle.passwordSafe.passwords.Password;

public interface IPasswordRepository extends JpaRepository<Password, Long> {
    Password findById(Integer id);
    Password findByName(String name);
}