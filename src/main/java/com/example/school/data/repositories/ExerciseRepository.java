package com.example.school.data.repositories;

import com.example.school.data.entities.Exercise;
import com.example.school.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Exercise getByName(String name);

    List<Exercise> getAllByUser(User user);
}
