package com.gabrielle.passwordSafe.encryption;

public interface ICipherAlgorithm {
    String encrypt(String password, String masterPassword);
    String decrypt(String password, String masterPassword);
}
