package com.example.school.service.services;

import com.example.school.service.models.HomeworkServiceModel;
import com.example.school.web.models.viewModels.HomeworkViewModel;
import com.example.school.web.models.viewModels.UserViewModel;

import java.time.LocalDateTime;
import java.util.List;

public interface HomeworkService {

    boolean createHomework(LocalDateTime now, String user, String gitAddress, String exercise);

    HomeworkServiceModel getRandomHomework();

    boolean isHasHomework();

    void deleteById(long id);

    List<String> getAllHomeworksNameByUser(UserViewModel user);
}
