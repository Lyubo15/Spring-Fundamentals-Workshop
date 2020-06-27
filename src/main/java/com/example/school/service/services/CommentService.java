package com.example.school.service.services;

import com.example.school.data.entities.Homework;
import com.example.school.service.models.HomeworkServiceModel;
import com.example.school.service.models.UserServiceModel;
import com.example.school.web.models.bindingModels.CommentBindingModel;
import com.example.school.web.models.viewModels.HomeworkViewModel;

import java.util.List;

public interface CommentService {

    void postComment(HomeworkServiceModel homework, CommentBindingModel commentBindingModel, String username);

}
