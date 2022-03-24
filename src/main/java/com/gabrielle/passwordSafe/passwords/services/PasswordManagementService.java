package com.gabrielle.passwordSafe.passwords.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.passwords.repositories.IPasswordRepository;
import com.gabrielle.passwordSafe.users.User;


@Service("passwordService")
public class PasswordManagementService implements IPasswordManagementService{

	@Autowired
	IPasswordRepository passwordRepository;
	
	@Transactional
	@Override
	public Integer createPassword(Password password) {
        password = passwordRepository.save(password);
        return password.getId();
	}
	

	@Override
	public Password findPassword(Integer passwordId) {
		return passwordRepository.findById(passwordId);
	}

}
