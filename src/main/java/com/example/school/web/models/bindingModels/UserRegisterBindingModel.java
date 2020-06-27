package com.example.school.web.models.bindingModels;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@ScriptAssert(lang = "javascript",
        script = "_this.confirmPassword !== null && _this.password === _this.confirmPassword",
        reportOn = "confirmPassword",
        message = "Passwords don't match")
public class UserRegisterBindingModel {

    private long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String git;

    public UserRegisterBindingModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Length(min = 2, message = "Username must be least 2 symbols")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Length(min = 2, message = "Password must be least 2 symbols")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Pattern(regexp = "(?=(https:/github.com/)).*", message = "Wrong get git url, must start with https:/github.com/")
    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }
}
