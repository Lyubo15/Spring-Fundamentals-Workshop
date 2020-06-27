package com.example.school.service.models;

import com.example.school.data.entities.Grade;
import com.example.school.data.entities.Role;

import java.util.List;

public class UserServiceModel {

    private long id;
    private String username;
    private String password;
    private String email;
    private String git;
    private Role role;
    private boolean checked;
    private List<Grade> grades;

    public UserServiceModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public double getAverage(){
        return this.getGrades().stream().mapToDouble(Grade::getGrade).sum() / this.getGrades().size();
    }
}
