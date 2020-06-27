package com.example.school.data.repositories;

import com.example.school.data.entities.Homework;
import com.example.school.data.entities.User;
import com.example.school.web.models.viewModels.HomeworkViewModel;
import com.example.school.web.models.viewModels.UserViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    Homework getHomeworkById(long id);

    Homework getHomeworkByGitAddressAndExerciseId(String name, long id);

    List<Homework> getAllByAuthor(User user);

    void deleteHomeworkById(long id);
}
