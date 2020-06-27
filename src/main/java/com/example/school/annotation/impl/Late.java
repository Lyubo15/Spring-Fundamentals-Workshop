package com.example.school.annotation.impl;

import com.example.school.annotation.IsLate;
import com.example.school.service.models.ExerciseServiceModel;
import com.example.school.service.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.Optional;

public class Late implements ConstraintValidator<IsLate, String> {

    private final AdminService adminService;

    @Autowired
    public Late(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean isValid(String exerciseName, ConstraintValidatorContext constraintValidatorContext) {
        Optional<ExerciseServiceModel> exercise = this.adminService.getExerciseByName(exerciseName);

        if (exercise.isEmpty()){
            return true;
        }

        return exercise.get().getDueDate().isAfter(LocalDateTime.now());
    }
}
