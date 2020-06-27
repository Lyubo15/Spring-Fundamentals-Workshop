package com.example.school.service.services;

import com.example.school.service.models.ExerciseServiceModel;
import com.example.school.web.models.bindingModels.ExerciseCreateBindingModel;
import com.example.school.web.models.bindingModels.RoleBindingModel;
import com.example.school.web.models.viewModels.UserViewModel;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Optional<ExerciseServiceModel> createExercise(ExerciseCreateBindingModel exerciseCreateBindingModel);

    List<ExerciseServiceModel> getAllExercise();

    void changeUserRole(RoleBindingModel roleBindingModel);

    Optional<ExerciseServiceModel> getExerciseByName(String name);

    List<String> getAllExercisesNameByUser(UserViewModel user);
}
