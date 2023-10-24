package com.udg.mirutina.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    
        //! 1.- Validate that Header be an Authorization validted
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer") ) {
          filterChain.doFilter(request, response);
          return;
        }

        //! 2.- Verify if JWT is valid
        String jwt = authHeader.split(" ")[1].trim();
                
        if( !this.jwtUtil.isValid(jwt) ) {
          filterChain.doFilter(request, response);
          return;
        }

        //! 3.- Load UserDetailsService in the user
        String username = this.jwtUtil.getUsername(jwt);
        User user = (User) this.userDetailsService.loadUserByUsername(username); 

        //! 4.- Load context security user
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          user.getUsername(), user.getPassword(), user.getAuthorities()
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println("-> " + authenticationToken);
        filterChain.doFilter(request, response);
  }

}
