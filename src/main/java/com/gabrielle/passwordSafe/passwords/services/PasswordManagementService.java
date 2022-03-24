package com.gabrielle.passwordSafe.passwords.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.gabrielle.passwordSafe.encryption.SecurityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.passwords.repositories.IPasswordRepository;

import java.util.List;


@Service("passwordService")
public class PasswordManagementService implements IPasswordManagementService{

	@Autowired
	IPasswordRepository passwordRepository;

	@Autowired
	SecurityService securityService;
	
	@Transactional
	@Override
	public Integer createPassword(Password password) {
		String masterPassword = password.getUser().getMasterPassword();
		if(masterPassword == null || masterPassword.isEmpty()) {
			return -1;
		}

		String encryptedPassword = securityService.encryptAccountPassword(password.getPassword(), masterPassword);
		password.setPassword(encryptedPassword);

        return passwordRepository.save(password).getId();
	}

	public Password findPassword(Integer passwordId) {
		return passwordRepository.findById(passwordId);
	}

	@Override
	@PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
	public List<Password> findUserPasswords(Integer userId) {
		return passwordRepository.findByUserId(userId);
	}

	@Override
	public Password findUserPassword(Integer userId, String passwordName) {
		return passwordRepository.findByUserIdAndName(userId, passwordName);
	}

	@Override
	public Password updatePassword(Integer userId, Password password) {
		Password savedPassword = passwordRepository.findById(password.getId());
		if (savedPassword != null) {
			password.setUser(savedPassword.getUser());
			createPassword(password);
		}
		return null;
	}

	@Override
	public void delete(Integer userId, String passwordName) {
		Password pwd = findUserPassword(userId, passwordName);
		passwordRepository.delete(pwd);
	}
}
