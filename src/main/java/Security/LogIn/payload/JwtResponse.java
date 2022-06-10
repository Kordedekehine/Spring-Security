package com.bicycleManagement.payload;

import com.bicycleManagement.model.Role;
import com.bicycleManagement.model.RoleName;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JwtResponse {
    //JwtResponse: { accessToken, tokenType, id, username, email, roles }

    @NotBlank
    private String accessToken;

    @NotNull
    private Long id;
    @NotBlank
    private String tokenType = " Bearer ";

    @NotBlank
    @Email
    private String email;

   @Column
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String tokenType, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.tokenType = tokenType;
        this.email = email;
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
