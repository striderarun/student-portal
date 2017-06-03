package com.arun.portal.graphql;

import com.arun.portal.entity.Student;
import com.arun.portal.service.StudentService;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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

    DataFetcher<List<Student>> allStudents = environment -> studentService.getAllStudents();

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("QueryType", typeWiring -> typeWiring
                        .dataFetcher("student", studentByName)
                        .dataFetcher("students", allStudents)
                ).build();
    }

}
