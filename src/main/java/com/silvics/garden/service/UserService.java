package com.silvics.garden.service;

import com.silvics.garden.model.User;
import com.silvics.garden.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public User registerUser(String userName, String password, String email) {
    LOG.info("Attempting to register user: {}", userName);

    if (userRepository.existsByUserName(userName)) {
      LOG.warn("Registration failed: Username '{}' already exists", userName);
      throw new RuntimeException("Username already exists");
    }

    if (userRepository.existsByEmail(email)) {
      LOG.warn("Registration failed: Email '{}' already exists", email);
      throw new RuntimeException("Email already exists");
    }

    String encodedPassword = passwordEncoder.encode(password);
    LOG.debug("Password encoded for user: {}", userName);

    User user = new User(userName, encodedPassword, email);
    User savedUser = userRepository.save(user);

    LOG.info("User registered successfully: {} with ID: {}", userName, savedUser.getId());
    return savedUser;
  }

  public Optional<User> findByUserName(String userName) {
    LOG.debug("Finding user by username: {}", userName);
    return userRepository.findByUserName(userName);
  }

  public Optional<User> findByEmail(String email) {
    LOG.debug("Finding user by email: {}", email);
    return userRepository.findByEmail(email);
  }

  public boolean existsByUserName(String userName) {
    return userRepository.existsByUserName(userName);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}