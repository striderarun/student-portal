package com.arun.portal.service.impl;

import com.arun.portal.entity.Student;
import com.arun.portal.repository.StudentRepository;
import com.arun.portal.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getAllStudentsByIds(List<Integer> ids) {
        return studentRepository.findByIdIn(ids);
    }

    @Override
    public List<Student> getStudentsByFirstName(String firstName) {
        return studentRepository.findByFirstName(firstName);
    }

    @Override
    public List<Student> getStudentsByGrade(String grade) {
        return studentRepository.findByGrade(grade);
    }

    @Override
    @Transactional
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student getStudentDetails(String firstName, String lastName) {
        return studentRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findOne(Integer.valueOf(id));
    }

}
