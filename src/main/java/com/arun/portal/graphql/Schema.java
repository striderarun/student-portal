package com.arun.portal.graphql;

import com.arun.portal.entity.Student;
import com.arun.portal.service.StudentService;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class Schema {

    @Autowired
    private StudentService studentService;

    GraphQLSchema graphQLSchema;

    @PostConstruct
    public void init() {
        try {
            SchemaParser schemaParser = new SchemaParser();
            ClassPathResource classPathResource = new ClassPathResource("schema.graphql");
            TypeDefinitionRegistry compiledSchema = schemaParser.parse(classPathResource.getFile());

            SchemaGenerator schemaGenerator = new SchemaGenerator();
            graphQLSchema = schemaGenerator.makeExecutableSchema(compiledSchema, buildRuntimeWiring());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public GraphQLSchema getSchema() {
        return graphQLSchema;
    }

    DataFetcher<List<Student>> studentByName = environment ->
            studentService.getStudentsByFirstName(environment.getArgument("name"));

    DataFetcher<List<Student>> studentsByGrade = environment ->
            studentService.getStudentsByGrade(environment.getArgument("grade"));

    DataFetcher<List<Student>> allStudents = environment -> studentService.getAllStudents();

    DataFetcher<MutationResult> addStudent = environment -> {
        Map<String, Object> argsMap = environment.getArgument("student");
        Student student = createStudentEntity(argsMap);
        studentService.addStudent(student);
        return new MutationResult();
    };

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("QueryType", typeWiring -> typeWiring
                        .dataFetcher("student", studentByName)
                        .dataFetcher("students", allStudents)
                        .dataFetcher("grade", studentsByGrade)
                ).type("MutationType", typeWiring -> typeWiring
                        .dataFetcher("addStudent", addStudent)
                ).build();
    }

    public static class MutationResult {
        public boolean success = true;
    }

    private Student createStudentEntity(Map<String, Object> argsMap) {
        Student student = new Student();
        student.setFirstName((String)argsMap.get("firstName"));
        student.setLastName((String)argsMap.get("lastName"));
        student.setAge((Integer)argsMap.get("age"));
        student.setEmail((String)argsMap.get("email"));
        student.setGrade((String)argsMap.get("grade"));
        student.setGuardianName((String)argsMap.get("guardianName"));
        student.setBirthDate(new DateTime());
        return student;
    }

}
