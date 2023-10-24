package com.udg.mirutina.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
  
  @Autowired
  private JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

      http
              .csrf(csrf -> csrf.disable())
              .cors(withDefaults())
              .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

              //! Public endpoints
              // .authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/api/users").permitAll())
              .authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/api/exercise-record/challenge-completed/**").permitAll())
              .authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/api/roles/**").permitAll())

              .authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/api​/exercise-record​/new​/**").permitAll())

              //! Auth example
              // .authorizeRequests(requests -> requests.antMatchers("/api/**").permitAll())


              //! Descomentar cuando el login
              .authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/api/auth/**").permitAll())
              .authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/api/users").permitAll())
              .authorizeRequests(requests -> requests.antMatchers(
                      "/v3/api-docs/**",
                      "/swagger-ui/**",
                      "/swagger-ui.html/**",
                      "/swagger-resources/**",
                      "/configuration/**").permitAll()

              // Default JSON when server is running


              .anyRequest()
              // .permitAll();
              .authenticated())
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
