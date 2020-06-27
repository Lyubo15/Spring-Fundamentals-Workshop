package com.example.school.service.services.impl;

import com.example.school.data.entities.Exercise;
import com.example.school.data.entities.Homework;
import com.example.school.data.entities.User;
import com.example.school.data.repositories.HomeworkRepository;
import com.example.school.service.models.HomeworkServiceModel;
import com.example.school.service.services.AdminService;
import com.example.school.service.services.HomeworkService;
import com.example.school.service.services.UserService;
import com.example.school.web.models.viewModels.HomeworkViewModel;
import com.example.school.web.models.viewModels.UserViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    private final UserService userService;
    private final AdminService adminService;
    private final ModelMapper modelMapper;
    private final HomeworkRepository homeworkRepository;

    @Autowired
    public HomeworkServiceImpl(UserService userService, AdminService adminService, ModelMapper modelMapper, HomeworkRepository homeworkRepository) {
        this.userService = userService;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
        this.homeworkRepository = homeworkRepository;
    }

    @Override
    public boolean createHomework(LocalDateTime now, String username, String gitAddress, String exerciseName) {

        User user = this.modelMapper.map(this.userService.getUserByUsername(username), User.class);
        Exercise exercise = this.modelMapper.map(this.adminService.getExerciseByName(exerciseName).get(), Exercise.class);
        List<Homework> homeworks = this.homeworkRepository.getAllByAuthor(user);

        if (this.isHomeworkExist(homeworks , exercise)) {
            return false;
        }

        Homework homework = new Homework();
        homework.setAuthor(user);
        homework.setAddedOn(now);
        homework.setExercise(exercise);
        homework.setGitAddress(gitAddress);

        this.homeworkRepository.saveAndFlush(homework);
        return true;
    }

    private boolean isHomeworkExist(List<Homework> homeworks, Exercise exercise) {
        if (!homeworks.isEmpty()) {
           return homeworks.stream().anyMatch(homework -> homework.getExercise().getId() == exercise.getId());
        }
        return false;
    }

    @Override
    public HomeworkServiceModel getRandomHomework() {
        Random random = new Random();
        int randomCount = random.nextInt(this.homeworkRepository.findAll().size()) + 1;
        List<Homework> homework = this.homeworkRepository.findAll();
        Collections.shuffle(homework);
        return this.modelMapper.map(homework.get(randomCount - 1), HomeworkServiceModel.class);
    }



    @Override
    public boolean isHasHomework() {
        return this.homeworkRepository.findAll().size() > 0;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        this.homeworkRepository.deleteHomeworkById(id);
    }

    @Override
    public List<String> getAllHomeworksNameByUser(UserViewModel user) {
        List<Homework> homeworks = this.homeworkRepository.getAllByAuthor(this.modelMapper.map(user, User.class));
        List<String> names = new ArrayList<>();
        homeworks.forEach(homeworkServiceModel -> names.add(homeworkServiceModel.getExercise().getName()));
        return names;
    }

}
