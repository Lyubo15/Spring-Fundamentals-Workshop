package com.example.school.annotation.impl;

import com.example.school.annotation.GitIsOnCurrentUser;
import com.example.school.data.entities.User;
import com.example.school.service.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsGitOnCurrentUser implements ConstraintValidator<GitIsOnCurrentUser, String> {

    private final HttpSession session;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public IsGitOnCurrentUser(HttpSession session, UserService userService, ModelMapper modelMapper) {
        this.session = session;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean isValid(String git, ConstraintValidatorContext constraintValidatorContext) {
        User user = this.modelMapper.map(this.userService.getUserByUsername(session.getAttribute("username").toString()), User.class);
        return user.getGit().equals(git);
    }
}
