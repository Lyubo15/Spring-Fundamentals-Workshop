package com.example.school.service.services;

import com.example.school.service.models.UserLoginServiceModel;
import com.example.school.service.models.UserRegisterServiceModel;
import com.example.school.service.models.UserServiceModel;
import com.example.school.web.models.bindingModels.UserLoginBindingModel;
import com.example.school.web.models.bindingModels.UserRegisterBindingModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserRegisterServiceModel> register(UserRegisterBindingModel userRegisterBindingModel);

    Optional<UserLoginServiceModel> login(UserLoginBindingModel userModel);

    UserServiceModel getUserByUsername(String username);

    List<UserServiceModel> getAllUsersWithoutCurrent(String currentUser);

    List<UserServiceModel> getFirstThreeUserOrderByAvgGradesAndUsername();
}
