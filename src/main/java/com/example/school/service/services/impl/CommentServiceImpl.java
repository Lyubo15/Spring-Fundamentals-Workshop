package com.example.school.service.services.impl;

import com.example.school.data.entities.Comment;
import com.example.school.data.entities.Grade;
import com.example.school.data.entities.Homework;
import com.example.school.data.entities.User;
import com.example.school.data.repositories.CommentRepository;
import com.example.school.data.repositories.HomeworkRepository;
import com.example.school.data.repositories.UserRepository;
import com.example.school.service.models.HomeworkServiceModel;
import com.example.school.service.models.UserServiceModel;
import com.example.school.service.services.CommentService;
import com.example.school.web.models.bindingModels.CommentBindingModel;
import com.example.school.web.models.viewModels.HomeworkViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final HomeworkRepository homeworkRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, UserRepository userRepository, HomeworkRepository homeworkRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.homeworkRepository = homeworkRepository;
    }

    @Override
    public void postComment(HomeworkServiceModel homeworkServiceModel, CommentBindingModel commentBindingModel, String username) {
        Comment comment = this.modelMapper.map(commentBindingModel, Comment.class);
        User user = this.userRepository.getByUsername(username);
        Homework homework = this.homeworkRepository.getHomeworkByGitAddressAndExerciseId(homeworkServiceModel.getGitAddress(), homeworkServiceModel.getExercise().getId());

        Grade grade = new Grade();
        grade.setGrade(comment.getScore());
        grade.setUsers(List.of(user));

        user.setGrades(List.of(grade));

        comment.setHomework(homework);
        comment.setAuthor(user);

        homework.setChecked(true);

        this.userRepository.saveAndFlush(user);
        this.homeworkRepository.saveAndFlush(homework);
        this.commentRepository.saveAndFlush(comment);
    }
}
