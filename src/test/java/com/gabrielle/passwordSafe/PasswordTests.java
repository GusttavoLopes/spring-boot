package com.gabrielle.passwordSafe;

import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.users.controllers.PasswordsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("test")
public class PasswordTests {
    //testar controller pela requisicao https://www.baeldung.com/spring-boot-testing
    @Autowired
    PasswordsController passwordsController;

    @Test
    void contextLoads() {}

    @Test
    void testUserUpdatePasswordOk() {
        Password pwd = new Password();
        pwd.setName("banking account");
        pwd.setPassword("14142");
        pwd.setId(1);
        ResponseEntity put = passwordsController.updatePassword(1, pwd);
        ResponseEntity<Password> get = passwordsController.findPasswordByUser(1, pwd.getName());
        Password resPwd = get.getBody();
        assertEquals(HttpStatus.OK, put.getStatusCode());
        assertEquals(HttpStatus.OK, get.getStatusCode());
        assertEquals(pwd.getName(), resPwd.getName());
        assertEquals(pwd.getId(), resPwd.getId());
    }

    @Test
    void testDeletePasswordOk() {
        ResponseEntity res = passwordsController.delete(1, "banking");
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    void testDeletePasswordFailsWithWrongName() {
        ResponseEntity res = passwordsController.delete(1, "does_not_exist");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    void testDeletePasswordFailsWithWrongUserId() {
        ResponseEntity res = passwordsController.delete(999, "banking");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }
}
