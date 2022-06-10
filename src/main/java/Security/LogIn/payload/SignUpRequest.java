package com.bicycleManagement.payload;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignUpRequest {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   @NotBlank
   @Size(min = 4,max = 40)
    private String firstName;

    @NotBlank
    @Size(min = 4,max = 40)
    private String lastName;
   @NotBlank
   @Size(max = 40)
   @Email
    private String email;

   @NotBlank
   @Size(min = 2,max = 15)
    private String username;

   @NotBlank
   @Size(min = 9,max = 11)
   private String phoneNumber;

   @NotBlank
   @Size(min = 6,max = 10)
    private String password;

   private Set<String> role;

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }
}
