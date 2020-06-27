package com.example.school.web.models.bindingModels;

import com.example.school.annotation.GitIsOnCurrentUser;
import com.example.school.annotation.IsLate;
import com.example.school.annotation.NotValue;

public class HomeworkBindingModel {

    private long id;
    private String exercise;
    private String gitAddress;

    public HomeworkBindingModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @NotValue(wrongValue = "Select exercise", message = "You have to choose value")
    @IsLate(message = "You late")
    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    @GitIsOnCurrentUser(message = "Invalid github")
    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }
}
