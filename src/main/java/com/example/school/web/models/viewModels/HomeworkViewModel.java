package com.example.school.web.models.viewModels;

import com.example.school.data.entities.Exercise;
import com.example.school.data.entities.User;

public class HomeworkViewModel {

    private long id;
    private String gitAddress;
    private boolean checked;
    private Exercise exercise;
    private User author;


    public HomeworkViewModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
