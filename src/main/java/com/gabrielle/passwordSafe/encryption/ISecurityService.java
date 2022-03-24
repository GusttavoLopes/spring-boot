package com.gabrielle.passwordSafe.encryption;

public interface ISecurityService {
    String hashMasterPassword(String masterPassword);
    boolean masterPasswordMatchesHash(String masterPassword, String hash);

    String encryptAccountPassword(String password, String masterPassword);
    String decryptAccountPassword(String password, String masterPassword);
}
