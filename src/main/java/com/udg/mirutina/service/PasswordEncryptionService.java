package com.udg.mirutina.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncryptionService {

  private final PasswordEncoder passwordEncoder;

  public PasswordEncryptionService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public String encryptPassword(String plainTextPassword) {
    return passwordEncoder.encode(plainTextPassword);
  }

  public boolean matches(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

}
