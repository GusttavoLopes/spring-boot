package com.gabrielle.passwordSafe;

import com.gabrielle.passwordSafe.encryption.ISecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class SecurityTests {
    @Autowired
    ISecurityService securityService;

    private static final String masterPassword = "teste123";


    @Test
    void testEncodeMasterPassword() {
        String hash = securityService.hashMasterPassword(masterPassword);

        assertNotEquals(masterPassword, hash);
    }

    @Test
    void testMasterPasswordMatchesHash() {
        String hash = securityService.hashMasterPassword(masterPassword);

        boolean match = securityService.masterPasswordMatchesHash(masterPassword, hash);
        assertTrue(match);
    }

    @Test
    void testMasterPasswordMatchesOtherMasterPasswordHash() {
        String otherMasterPassword = "masterPassword123";
        String hash = securityService.hashMasterPassword(otherMasterPassword);

        boolean match = securityService.masterPasswordMatchesHash(masterPassword, hash);
        assertFalse(match);
    }

    @Test
    void testEncryptPasswordWithMasterPassword() {
        String password = "password123";

        String encryptedPassword = securityService.encryptAccountPassword(password, masterPassword);
        assertNotEquals(password, encryptedPassword);
    }

    @Test
    void testDecryptPasswordWithMasterPassword() {
        String password = "password123";

        String encryptedPassword = securityService.encryptAccountPassword(password, masterPassword);
        String decryptedPassword = securityService.decryptAccountPassword(encryptedPassword, masterPassword);

        assertEquals(password, decryptedPassword);
    }

    @Test
    void testDecryptPasswordWithOtherMasterPassword() {
        String password = "password123";
        String otherMasterPassword = "masterPassword123";

        String encryptedPassword = securityService.encryptAccountPassword(password, masterPassword);
        String decryptedPassword = securityService.decryptAccountPassword(encryptedPassword, otherMasterPassword);

        assertNotEquals(password, decryptedPassword);
    }

}
