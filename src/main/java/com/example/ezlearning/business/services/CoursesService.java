package com.example.ezlearning.business.services;

import com.example.ezlearning.business.entity.Courses;
import com.example.ezlearning.business.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {
    private CoursesRepository coursesRepository;

    @Autowired
    public CoursesService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public void add(Courses courses) throws Exception{
        if (null != coursesRepository.findByNameCourses(courses.getName())) {
            throw new Exception("A course with the name already exists " + courses.getName());
        }
    coursesRepository.save(courses);
    }

    public void delete(Courses courses){
        coursesRepository.delete(courses);
    }

    public List<Courses> getAll(){
        return coursesRepository.findAll();
    }
}
