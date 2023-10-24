package com.udg.mirutina.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.udg.mirutina.model.User;

public interface UserRepository extends CrudRepository<User, Serializable> {
  
  List<User> findAll();

  User findById(Long id);

  User findByUsername(String username);

  void deleteById(Long id);

  @SuppressWarnings("unchecked")
  User save(User user);

}
