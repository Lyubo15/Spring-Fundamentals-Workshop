package com.example.school.web.models.bindingModels;

import com.example.school.annotation.NotValue;

public class RoleBindingModel {

    private String username;
    private String role;

    public RoleBindingModel() {
    }

    @NotValue(wrongValue = "Select user", message = "You must to chose user")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
