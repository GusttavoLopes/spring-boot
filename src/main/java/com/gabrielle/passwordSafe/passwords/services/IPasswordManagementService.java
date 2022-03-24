package com.gabrielle.passwordSafe.passwords.services;

import com.gabrielle.passwordSafe.passwords.Password;

import java.util.List;

public interface IPasswordManagementService {
	Integer createPassword(Password password);
	List<Password> findUserPasswords(Integer userId);
	Password findUserPassword(Integer userId, String passwordName);
	Password updatePassword(Integer userId, Password password);
	void delete(Integer userId, String passwordName);
}
