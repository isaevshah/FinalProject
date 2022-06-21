package com.example.ezlearning.business.repository;

import com.example.ezlearning.auth.entity.User;
import com.example.ezlearning.business.entity.Courses;
import com.example.ezlearning.business.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findAllByCourses(Courses courses);
    List<Education> findAllByUsername(User user);
    Education findByCoursesAndUsername(Courses courses, User user);
}
