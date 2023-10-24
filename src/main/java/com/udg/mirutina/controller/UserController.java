package com.udg.mirutina.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udg.mirutina.model.Role;
import com.udg.mirutina.model.User;
import com.udg.mirutina.pojo.Response;
import com.udg.mirutina.service.PasswordEncryptionService;
import com.udg.mirutina.service.ResponseSerivce;
import com.udg.mirutina.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private PasswordEncryptionService passwordEncryptionService;

  @Autowired
  private UserService userService;

  @Autowired
  private ResponseSerivce resService;

  @GetMapping
  public ResponseEntity<Response> getUsers() {
    try {
      List<User> users = this.userService.findAll();
      return new ResponseEntity<Response>(new Response(true, "success", users), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping("/username/{username}")
  public ResponseEntity<Response> getUserByUsername(@PathVariable String username) {
    try {
      User dbUser = this.userService.findByUsername( username.trim() );

      if( dbUser == null ) {
        String message = User.class.getSimpleName() + " with username: " + username + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return new ResponseEntity<Response>(new Response(true, "success", dbUser), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/user/roles/{userId}")
  public ResponseEntity<Response> getRolesByUserId(@PathVariable Long userId) {
    try {
      User dbUser = this.userService.findById(userId);
      if( dbUser == null ) {
        String message = User.class.getSimpleName() + " with id: " + userId + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      List<Role> roles = dbUser.getRoles();

      return new ResponseEntity<Response>(new Response(true, "success", roles), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response> getUserById(@PathVariable Long id) {
    try {
      User dbUser = this.userService.findById( id );

      if(dbUser == null) {
        String message = User.class.getSimpleName() + " with id: " + id + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return new ResponseEntity<Response>(new Response(true, "success", dbUser), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<Response> createUser(@RequestBody User user) {
    try {
      String encryptedPassword = passwordEncryptionService.encryptPassword(user.getPassword());
      user.setPassword(encryptedPassword);
      
      User userUpdated = this.userService.save(user);
      return new ResponseEntity<Response>(new Response(true, "created", userUpdated), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id ,@RequestBody User user) {
    try {

      //Validate if exist user
      User dbUser = this.userService.findById(id);
      if(dbUser == null) {
        String message = User.class.getSimpleName() + " with id: " + id + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      // Update user
      dbUser.setUsername(user.getUsername() );
      dbUser.setEmail( user.getEmail());
      dbUser.setGoogle( user.getGoogle());
      dbUser.setIsActive( user.getIsActive());
      dbUser.setRoles( user.getRoles() );

      User userUpdated = this.userService.save(dbUser);
      return new ResponseEntity<Response>(new Response(true, "updated", userUpdated), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response> deleteUserById(@PathVariable Long id) {
    try {
      User dbUser = this.userService.findById( id );
      
      if(dbUser == null) {
        String message = User.class.getSimpleName() + " with id: " + id + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(message)), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      this.userService.deleteById(id);
      return new ResponseEntity<Response>(new Response(true, "deleted", dbUser), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.resService.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
