package com.arun.portal.controller;

import com.arun.portal.entity.Student;
import com.arun.portal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/services/student")
public class GraphQLGateway {

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Student> findAllStudents() {
        return studentService.getAllStudents();
    }

    @RequestMapping(value= "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Student> fetchStudentsByFirstName(@PathVariable String name) {
        return studentService.getStudentsByFirstName(name);
    }


}
