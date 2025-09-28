package com.silvics.garden.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(authz -> authz
        .requestMatchers("/api/users/register").permitAll()
        .requestMatchers("/api/garden/**").permitAll()
        .requestMatchers("/h2-console/**").permitAll()
        .requestMatchers("/hello").permitAll()
        .anyRequest().authenticated()
      )
      .headers(headers -> headers.frameOptions().disable());

    return http.build();
  }
}