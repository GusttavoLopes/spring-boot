package com.gabrielle.passwordSafe;

import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.controllers.UserDTO;
import com.gabrielle.passwordSafe.users.controllers.UsersController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class UserTests {
    @Autowired
    UsersController usersController;

    private User getTestUser() {
        return  new User("nome", "email@email.com", "123456");
    }

    @Test
    void contextLoads() {}

    @Test
    void  testUserCreationSuccess() {
        User user = getTestUser();
        ResponseEntity<Integer> response = usersController.saveUser(user);
        Integer savedUserId = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        boolean isValidId = savedUserId > 0;
        assertTrue(isValidId);
    }

    @Test
    void testUserCreationFails() {
        User user = getTestUser();
        user.setEmail("other@email.com");
        ResponseEntity<Integer> firstResp = usersController.saveUser(user);
        assertEquals(firstResp.getStatusCode(), HttpStatus.CREATED);
        ResponseEntity<Integer> secondResp = usersController.saveUser(user);
        assertEquals(secondResp.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(secondResp.getBody(),-1);
    }

    @Test
    void testUserRetrieval() {
        User user = getTestUser();
        ResponseEntity<Integer> save = usersController.saveUser(user);
        assertTrue(save.getBody() > 0);
        ResponseEntity<UserDTO> res = usersController.findUser(user.getId());
        UserDTO userDTO = res.getBody();
        assertEquals(userDTO.name, user.getName());
    }

    @Test
    void testInvalidUser() {
        User user = getTestUser();
        user.setEmail(null);
        user.setName(null);
        user.setMasterPassword(null);
        ResponseEntity<Integer> res = usersController.saveUser(user);
        assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(res.getBody(), -1);
    }
}
