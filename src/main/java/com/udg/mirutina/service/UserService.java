package com.udg.mirutina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udg.mirutina.model.User;
import com.udg.mirutina.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  
  // @Secured("ROLE_ADMIN")
  @Transactional(readOnly = true)
  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  @Transactional(readOnly = true)
  public User findByUsername(String username) {
    return this.userRepository.findByUsername(username);
  }

  @Transactional(readOnly = true)
  public User findById(Long id) {
    return this.userRepository.findById(id);
  }

  @Transactional
  public User save(User user) {
    return this.userRepository.save(user);
  }

  @Transactional
  public void deleteById(Long id) {
    this.userRepository.deleteById(id);
  }

}
