package com.example.school.service.services.impl;

import com.example.school.data.entities.Exercise;
import com.example.school.data.entities.User;
import com.example.school.data.repositories.ExerciseRepository;
import com.example.school.data.repositories.UserRepository;
import com.example.school.service.models.ExerciseServiceModel;
import com.example.school.service.services.AdminService;
import com.example.school.service.services.RoleService;
import com.example.school.service.services.UserService;
import com.example.school.web.models.bindingModels.ExerciseCreateBindingModel;
import com.example.school.web.models.bindingModels.RoleBindingModel;
import com.example.school.web.models.viewModels.UserViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final RoleService roleService;
    private final HttpSession session;

    @Autowired
    public AdminServiceImpl(ExerciseRepository exerciseRepository, UserRepository userRepository, ModelMapper modelMapper, UserService userService, RoleService roleService, HttpSession session) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.session = session;
    }

    @Override
    public Optional<ExerciseServiceModel> createExercise(ExerciseCreateBindingModel exerciseCreateBindingModel) {
        Exercise exercise = this.modelMapper.map(exerciseCreateBindingModel, Exercise.class);
        User user = this.modelMapper.map(this.userService.getUserByUsername(session.getAttribute("username").toString()), User.class);
        exercise.setUser(user);
        this.exerciseRepository.saveAndFlush(exercise);
        return Optional.of(this.modelMapper.map(exercise, ExerciseServiceModel.class));
    }

    @Override
    public List<ExerciseServiceModel> getAllExercise() {
        return this.exerciseRepository
                .findAll()
                .stream()
                .map(exercise -> this.modelMapper.map(exercise, ExerciseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void changeUserRole(RoleBindingModel roleBindingModel) {
        User user = this.modelMapper.map(this.userService.getUserByUsername(roleBindingModel.getUsername()), User.class);
        user.setRole(this.roleService.getRole(roleBindingModel.getRole()));
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public Optional<ExerciseServiceModel> getExerciseByName(String name) {
        Exercise exercise = this.exerciseRepository.getByName(name);

        if (exercise == null){
            return Optional.empty();
        }

        return Optional.of(this.modelMapper.map(exercise, ExerciseServiceModel.class));
    }

    @Override
    public List<String> getAllExercisesNameByUser(UserViewModel user) {
        List<Exercise> exercises = this.exerciseRepository.getAllByUser(this.modelMapper.map(user, User.class));
        List<String> names = new ArrayList<>();
        exercises.forEach(exercise -> names.add(exercise.getName()));
        return names;
    }
}
