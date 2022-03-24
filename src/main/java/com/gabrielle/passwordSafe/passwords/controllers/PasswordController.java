package com.gabrielle.passwordSafe.passwords.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielle.passwordSafe.passwords.Password;
import com.gabrielle.passwordSafe.passwords.services.IPasswordManagementService;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.controllers.UserDTO;

@RestController
@RequestMapping(value = "/password")
@CrossOrigin


public class PasswordController {
	
	@Autowired
	IPasswordManagementService passwordService;
	
	@PostMapping(produces = "application/json")
	public ResponseEntity<Integer> saveUser(@RequestBody Password password) {

        Integer passwordId = passwordService.createPassword(password);
        if (passwordId > 0) {
            return new ResponseEntity(passwordId, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
	
    @GetMapping(value = "/{passwordId}", produces = "application/json")
    public ResponseEntity<UserDTO> findPassword(Integer passwordId) {
        Password password= passwordService.findPassword(passwordId);
        return new ResponseEntity(PasswordDTO.create(password), HttpStatus.OK);
    }
	

}
