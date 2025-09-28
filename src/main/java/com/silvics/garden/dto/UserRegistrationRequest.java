package com.silvics.garden.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegistrationRequest {

  @JsonProperty("userName")
  private String userName;

  @JsonProperty("password")
  private String password;

  @JsonProperty("email")
  private String email;

  public UserRegistrationRequest() {}

  public UserRegistrationRequest(String userName, String password, String email) {
    this.userName = userName;
    this.password = password;
    this.email = email;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}