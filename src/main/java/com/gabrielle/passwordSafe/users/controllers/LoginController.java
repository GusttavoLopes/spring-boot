package com.gabrielle.passwordSafe.users.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.gabrielle.passwordSafe.security.JwtUtils;
import com.gabrielle.passwordSafe.security.Login;
import com.gabrielle.passwordSafe.users.User;
import com.gabrielle.passwordSafe.users.services.IUserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private IUserManagementService userManagementService;

    @PostMapping()
    public ResponseEntity<Login> autenticar(@RequestBody Login login) throws JsonProcessingException {
        Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        User user = userManagementService.findUserByEmail(auth.getName());
        login.setPassword(null);
        login.setName(user.getName());
        login.setErrors(user.loginTries);
        login.setAutorizacao(user.getRoleNames()[0]);
        try {
            if(user.loginTries >= 3) {
                return new ResponseEntity(login, HttpStatus.UNAUTHORIZED);
            }
            auth = authManager.authenticate(auth);
            login.setToken(JwtUtils.generateToken(auth));
            user.loginTries = 0;
            userManagementService.save(user);
            return ResponseEntity.ok(login);
        } catch (AuthenticationException e) {
            user.loginTries++;
            userManagementService.save(user);
            login.setErrors(user.loginTries);
            return new ResponseEntity(login, HttpStatus.FORBIDDEN);
        }
    }

}
