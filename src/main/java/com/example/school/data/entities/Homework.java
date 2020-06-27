package com.example.school.data.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "homeworks")
public class Homework extends BaseEntity{

    private LocalDateTime addedOn;
    private String gitAddress;
    private User author;
    private Exercise exercise;
    private boolean checked;
    private Comment comment;

    public Homework() {
    }

    @Column(name = "added_on")
    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    @Column(name = "git_address")
    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Column(name = "checked")
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @OneToOne(mappedBy = "homework", targetEntity = Comment.class, cascade = CascadeType.ALL)
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
