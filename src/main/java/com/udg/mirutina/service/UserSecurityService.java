package com.udg.mirutina.service;

import java.util.ArrayList;
// import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udg.mirutina.model.Role;
import com.udg.mirutina.model.User;
import com.udg.mirutina.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User dbUser = this.userRepository.findByUsername(username);

    if( dbUser == null) throw new UsernameNotFoundException("User: "+ username +", was not found");

    List<Role> userRoles  = dbUser.getRoles();
    List<String> rolesList    = new ArrayList<>();
    // String[] rolesArray = rolesList.toArray(new String[0]);

    for( Role userRole : userRoles ) {
      rolesList.add(userRole.getName());
    }

    // Generate password -> https://bcrypt.online/
    return org.springframework.security.core.userdetails.User.
      builder()
      .username(dbUser.getUsername())
      .password(dbUser.getPassword())
      .authorities(this.grantedAuthorities(rolesList.toArray(new String[0])))
      // .roles(rolesArray)
      // .accountLocked(false)
      // .disabled(false)
      .build();
  }

  private String[] getAuthorities(String role) {
    if( "ADMIN".equals(role) || "CUSTOMER".equals(role) ) {
      return new String[] {"random_order"};
    }

    return new String[] {};
  }

  private List<GrantedAuthority> grantedAuthorities(String[] roles) {
    List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

    for(String role: roles) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

      for(String authority : this.getAuthorities(role)) {
        authorities.add(new SimpleGrantedAuthority(authority));
      }
    }

    return authorities;
  }

}
