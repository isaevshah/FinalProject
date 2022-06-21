package com.example.ezlearning.business.controllers;

import com.example.ezlearning.business.entity.Courses;
import com.example.ezlearning.business.entity.Professor;
import com.example.ezlearning.business.repository.ProfessorRepository;
import com.example.ezlearning.business.services.CoursesService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CoursesController {
    private CoursesService coursesService;
    private ProfessorRepository professorRepository;

    @GetMapping("/add/{id_professor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCourses(@PathVariable Long id_professor, Model model) {
        try {
            Professor current = professorRepository.findById(id_professor).get();
            model.addAttribute("courses");
            model.addAttribute("professor", current);
            return "courses/courses-add";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
