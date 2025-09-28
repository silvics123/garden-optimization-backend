package com.silvics.garden.controller;

import com.silvics.garden.dto.UserRegistrationRequest;
import com.silvics.garden.model.User;
import com.silvics.garden.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
    LOG.info("Received user registration request for username: {}", request.getUserName());

    try {
      User user = userService.registerUser(
        request.getUserName(),
        request.getPassword(),
        request.getEmail()
      );

      LOG.info("User registration successful for: {}", user.getUserName());

      User responseUser = new User();
      responseUser.setId(user.getId());
      responseUser.setUserName(user.getUserName());
      responseUser.setEmail(user.getEmail());
      responseUser.setCreatedAt(user.getCreatedAt());
      responseUser.setUpdatedAt(user.getUpdatedAt());

      return ResponseEntity.ok(responseUser);
    }
    catch (RuntimeException e) {
      LOG.error("User registration failed for username: {}", request.getUserName(), e);
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    catch (Exception e) {
      LOG.error("Unexpected error during user registration", e);
      return ResponseEntity.internalServerError().body("Registration failed due to an unexpected error");
    }
  }
}