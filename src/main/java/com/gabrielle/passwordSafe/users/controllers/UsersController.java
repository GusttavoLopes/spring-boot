package com.gabrielle.passwordSafe.users.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.services.IUserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin
public class UsersController {
    @Autowired
    IUserManagementService userManagementService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<Integer> saveUser(@RequestBody UserCreationDTO userDTO) {
        User user = userManagementService.createUser(userDTO);
        if (user != null) {
            return new ResponseEntity(user.getId(), HttpStatus.CREATED);
        }
        return new ResponseEntity(-1, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    @JsonView({View.User.class})
    public ResponseEntity<User> findUser(@PathVariable() Integer userId) {
        User user = userManagementService.findUser(userId);
        if(user == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }
}
