package com.example.school.service.services.impl;

import com.example.school.data.entities.User;
import com.example.school.data.repositories.UserRepository;
import com.example.school.service.models.UserLoginServiceModel;
import com.example.school.service.models.UserRegisterServiceModel;
import com.example.school.service.models.UserServiceModel;
import com.example.school.service.services.RoleService;
import com.example.school.service.services.UserService;
import com.example.school.web.models.bindingModels.UserLoginBindingModel;
import com.example.school.web.models.bindingModels.UserRegisterBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<UserRegisterServiceModel> register(UserRegisterBindingModel userModel) {
        if (this.userRepository.getByUsername(userModel.getUsername()) != null) {
            return Optional.empty();
        }

        User user = this.modelMapper.map(userModel, User.class);
        user.setRole(this.userRepository.count() == 0 ? this.roleService.getRole("TEACHER") : this.roleService.getRole("STUDENT"));

        this.userRepository.saveAndFlush(user);

        return Optional.of(this.modelMapper.map(user, UserRegisterServiceModel.class));
    }

    @Override
    public Optional<UserLoginServiceModel> login(UserLoginBindingModel userModel) {
        User user = this.userRepository.getByUsername(userModel.getUsername());

        if (user == null) {
            return Optional.empty();
        }

        if(!user.getPassword().equals(userModel.getPassword())){
            return Optional.empty();
        }

        return Optional.of(this.modelMapper.map(user, UserLoginServiceModel.class));
    }


    @Override
    public UserServiceModel getUserByUsername(String username) {
        return this.modelMapper.map(this.userRepository.getByUsername(username), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> getAllUsersWithoutCurrent(String currantUser) {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .filter(userServiceModel -> !userServiceModel.getUsername().equals(currantUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserServiceModel> getFirstThreeUserOrderByAvgGradesAndUsername(){
        return this.userRepository
                .findAll()
                .stream()
                .limit(3)
                .filter(user -> !user.getRole().getAuthority().equals("TEACHER"))
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .sorted(Comparator.comparing(UserServiceModel::getAverage).thenComparing(UserServiceModel::getUsername))
                .collect(Collectors.toList());
    }
}
