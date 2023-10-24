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
import com.udg.mirutina.pojo.Response;
import com.udg.mirutina.service.ResponseSerivce;
import com.udg.mirutina.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @Autowired
  private ResponseSerivce responseSerivce;

  @GetMapping
  public ResponseEntity<Response> getRoles() {
    try {
      List<Role> roles = this.roleService.findAll();
      return new ResponseEntity<Response>(new Response(true, "success", roles), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response> getRoleById(@PathVariable Long id) {
    try {
      Role dbRole = this.roleService.findById(id);

      if (dbRole == null) {
        String message = Role.class.getSimpleName() + " with id: " + id + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(message)), HttpStatus.OK);
      }

      return new ResponseEntity<Response>(new Response(true, "success", dbRole), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/name/{roleName}")
  public ResponseEntity<Response> getRoleByName(@PathVariable String roleName) {
    try {
      Role dbRole = this.roleService.findByName(roleName.trim().toUpperCase());

      if (dbRole == null) {
        String message = Role.class.getSimpleName() + " with name: " + roleName + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(message)), HttpStatus.OK);
      }

      return new ResponseEntity<Response>(new Response(true, "success", dbRole), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<Response> createRole(@RequestBody Role role) {
    try {
      role.setName( role.getName().trim().toUpperCase() );
      role.setDescription( role.getDescription().trim() );

      Role newRole = this.roleService.save(role);
      return new ResponseEntity<Response>(new Response(true, "created", newRole), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response> updateRole(@PathVariable Long id, @RequestBody Role role) {
    try {
      Role dbRole = this.roleService.findById(id);

      if (dbRole == null) {
        String message = Role.class.getSimpleName() + " with id: " + id + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(message)), HttpStatus.OK);
      }

      // System.out.println("=== " + dbRole);
      dbRole.setId(dbRole.getId());
      dbRole.setName(role.getName().trim().toUpperCase() );
      dbRole.setDescription(role.getDescription().trim() );
      dbRole.setIsActive(role.getIsActive());

      Role roleUpdated = this.roleService.save(dbRole);
      return new ResponseEntity<Response>(new Response(true, "updated", roleUpdated), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response> deleteRoleById(@PathVariable Long id) {
    try {
      Role dbRole = this.roleService.findById(id);

      if (dbRole == null) {
        String message = Role.class.getSimpleName() + " with id: " + id + " not found";
        return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(message)), HttpStatus.OK);
      }

      this.roleService.deleteById(id);
      return new ResponseEntity<Response>(new Response(true, "deleted", dbRole), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Response>(new Response(false, "error", this.responseSerivce.errors(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
