package com.gabrielle.passwordSafe.users.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.passwords.controllers.PasswordDTO;
import com.gabrielle.passwordSafe.passwords.controllers.View;
import com.gabrielle.passwordSafe.passwords.services.IPasswordManagementService;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.services.IUserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/users/{userId}/password")
@CrossOrigin
public class PasswordsController {
    @Autowired
    IUserManagementService userManagementService;

    @Autowired
    IPasswordManagementService passwordManagementService;

    @PostMapping(value = "")
    public Integer addPassword(@PathVariable("userId") Integer userId, @RequestBody Password password) {
        password = userManagementService.addPassword(userId, password);
        if(password != null) {
            return password.getId();
        }
        return -1;
    }

    @PutMapping(value = "")
    public ResponseEntity<HttpStatus> updatePassword(@PathVariable("userId") Integer userId, @RequestBody Password password) {
        passwordManagementService.updatePassword(userId, password);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<PasswordDTO>> findPasswordsByUser(@PathVariable("userId") Integer userId) {
        List<Password> passwords = passwordManagementService.findUserPasswords(userId);
        List<PasswordDTO> dtos = new ArrayList<>();
        for(Password pwd : passwords) {
            dtos.add(PasswordDTO.create(pwd));
        }
        return new ResponseEntity<List<PasswordDTO>>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{passwordName}", produces = "application/json")
    @JsonView(View.Password.class)
    public ResponseEntity<Password> findPasswordByUser(
            @PathVariable("userId") Integer userId,
            @PathVariable("passwordName") String passwordName
    ) {
        Password pwd = passwordManagementService.findUserPassword(userId, passwordName);
        return new ResponseEntity<Password>(pwd, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{passwordName}", produces = "application/json")
    public ResponseEntity delete(
            @PathVariable("userId") Integer userId,
            @PathVariable("passwordName") String passwordName
    ) {
        try {
            passwordManagementService.delete(userId, passwordName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
