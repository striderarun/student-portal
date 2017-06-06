package com.arun.portal.repository;

import com.arun.portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFirstName(String firstName);

    List<Student> findByGrade(String grade);

    Student findByFirstNameAndLastName(String firstName, String lastName);
}
