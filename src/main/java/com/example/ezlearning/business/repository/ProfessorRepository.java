package com.example.ezlearning.business.repository;

import com.example.ezlearning.business.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor,Long> {

}
