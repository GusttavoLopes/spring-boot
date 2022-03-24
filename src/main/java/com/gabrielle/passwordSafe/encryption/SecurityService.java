package com.gabrielle.passwordSafe.encryption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service("securityService")
public class SecurityService implements ISecurityService {
    @Autowired
    BCryptPasswordEncoder bcrypt;

    @Autowired
    VigenereCipher cipher;

    @Override
    public String hashMasterPassword(String masterPassword) {
        return bcrypt.encode(masterPassword);
    }

    @Override
    public boolean masterPasswordMatchesHash(String masterPassword, String hash) {
        return bcrypt.matches(masterPassword, hash);
    }

    @Override
    public String encryptAccountPassword(String password, String masterPassword) {
        return cipher.encrypt(password, masterPassword);
    }

    @Override
    public String decryptAccountPassword(String password, String masterPassword) {
        return cipher.decrypt(password, masterPassword);
    }

}
