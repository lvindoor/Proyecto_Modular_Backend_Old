package com.udg.mirutina.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.udg.mirutina.model.Role;

public interface RoleRepository extends CrudRepository<Role, Serializable> {
  
  List<Role> findAll();

  Role findById(Long id);
    
  Role findByName(String name);

  void deleteById(Long id);

  @SuppressWarnings("unchecked")
  Role save(Role role);

}
