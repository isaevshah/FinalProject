package com.example.ezlearning.business.repository;

import com.example.ezlearning.business.entity.Courses;
import com.example.ezlearning.business.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursesRepository extends JpaRepository<Courses,Long> {
    Courses findByNameCourses(String name);
    List<Courses> findAllByProfessor(Professor professor);
}
