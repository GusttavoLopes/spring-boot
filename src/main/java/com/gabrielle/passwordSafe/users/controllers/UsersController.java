package com.gabrielle.passwordSafe.users.controllers;

import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.services.IUserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin
public class UsersController {
    @Autowired
    IUserManagementService userManagementService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<Integer> saveUser(@RequestBody User user) {

        Integer userId = userManagementService.createUser(user);
        if (userId > 0) {
            return new ResponseEntity(userId, HttpStatus.CREATED);
        }
        return new ResponseEntity(-1, HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<UserDTO> findUser(@PathVariable() Integer userId) {
        User user = userManagementService.findUser(userId);

        return new ResponseEntity(UserDTO.create(user), HttpStatus.OK);
    }
}
