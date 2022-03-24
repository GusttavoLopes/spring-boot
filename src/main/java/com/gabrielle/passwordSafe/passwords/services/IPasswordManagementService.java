package com.gabrielle.passwordSafe.passwords.services;

import com.gabrielle.passwordSafe.passwords.Password;

public interface IPasswordManagementService {
	
	Integer createPassword(Password password);
	Password findPassword(Integer passwordId);

}
