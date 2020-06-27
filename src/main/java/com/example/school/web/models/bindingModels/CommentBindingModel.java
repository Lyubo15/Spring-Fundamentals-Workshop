package com.example.school.web.models.bindingModels;

import com.example.school.annotation.NotValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

public class CommentBindingModel {

    private long id;
    private int score;
    private String textContent;

    public CommentBindingModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Min(value = 2, message = "Score must be between 2 and 6")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Length(min = 4, message = "Comment text content must be more than 3 characters!")
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
