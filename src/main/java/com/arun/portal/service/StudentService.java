package com.arun.portal.service;

import com.arun.portal.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    List<Student> getStudentsByFirstName(String firstName);

    void addStudent(Student student);
}
