package com.udg.mirutina.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udg.mirutina.config.JwtUtil;
import com.udg.mirutina.model.User;
import com.udg.mirutina.pojo.LoginDto;
import com.udg.mirutina.pojo.Response;
import com.udg.mirutina.pojo.UpdatePassword;
import com.udg.mirutina.service.PasswordEncryptionService;
import com.udg.mirutina.service.ResponseSerivce;
import com.udg.mirutina.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncryptionService passwordEncryptionService;

  @Autowired
  private ResponseSerivce resService;

  @PostMapping("/login")
  public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) {
    Map<String, Object> response = new HashMap<>();
    try {

      User dbUser = this.userService.findByUsername(loginDto.getUsername());

      if( dbUser == null ) {
        String message = User.class.getSimpleName() + " with username: " + loginDto.getUsername() + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
      Authentication authentication = this.authenticationManager.authenticate(login);
  
      String jwt = this.jwtUtil.create(loginDto.getUsername());

      response.put("jwt", jwt);
      response.put("user", dbUser);
      response.put("details", authentication.getDetails());
      return new ResponseEntity<>(new Response(true, "success", response), HttpStatus.OK);
      // return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    } catch (Exception e) {
      return new ResponseEntity<>(new Response(false, "error", resService.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
    @PostMapping("/update/password/{username}")
    public ResponseEntity<Response> updateUserPassword(@PathVariable String username ,@RequestBody UpdatePassword updatePassword) {
    try {

      if( !updatePassword.getNewPassword().trim().equals(updatePassword.getConfirmPassword().trim()) ) {
        String message = "New password and Confirm Password is not the same";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      User dbUser = this.userService.findByUsername(username);
      if(dbUser == null) {
        String message = User.class.getSimpleName() + " with username: " + username + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      if( !(dbUser.getUsername().toLowerCase().equals(username.trim().toLowerCase())) ) {
        String message = User.class.getSimpleName() + " with username: " + username + " is not de same that: " + dbUser.getUsername() + " and id:" + username;
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      boolean isPasswordMatch = passwordEncryptionService.matches(updatePassword.getPassword(), dbUser.getPassword());
      if(!isPasswordMatch) {
        String message = "Password is not correct";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      String encryptedPassword = passwordEncryptionService.encryptPassword(updatePassword.getNewPassword());
      dbUser.setPassword(encryptedPassword);

      User userUpdated = this.userService.save(dbUser);
      return new ResponseEntity<Response>(new Response(true, "password was changed", userUpdated), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
