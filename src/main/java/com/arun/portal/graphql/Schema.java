package com.arun.portal.graphql;

import com.arun.portal.entity.Student;
import com.arun.portal.service.StudentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import graphql.ExecutionResult;
import graphql.GraphQL;
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
import java.util.Collections;
import java.util.HashMap;
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

    DataFetcher<Student> studentById = environment ->
            studentService.getStudentById(environment.getArgument("id"));

    DataFetcher<Student> prefect = environment ->
            studentService.getStudentById(1);


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
                        .dataFetcher("prefect", prefect)
                        .dataFetcher("student", studentById)
                        .dataFetcher("students", allStudents)
                        .dataFetcher("studentByName", studentByName)
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

    public Map<String, Object> exec(String query, Map<String,Object> variables) {
        ExecutionResult result = new GraphQL(graphQLSchema).execute(query, (Object)null, variables);
        if (!result.getErrors().isEmpty()) {
            System.err.println(result.getErrors());
            throw new RuntimeException();
        }
        Map<String, Object> map = result.getData();
        return map;
    }


    private String query = "  query IntrospectionQuery {  "+
            "    __schema {  "+
            "      queryType { name }  "+
            "      mutationType { name }  "+
            "      types {  "+
            "        ...FullType        "+
            "      }        "+
            "      directives {        "+
            "        name        "+
            "        description        "+
            "        args {        "+
            "          ...InputValue        "+
            "        }        "+
            "        onOperation        "+
            "        onFragment        "+
            "        onField        "+
            "      }        "+
            "    }        "+
            "  }        "+
            "  fragment FullType on __Type {        "+
            "    kind        "+
            "    name        "+
            "    description        "+
            "    fields {        "+
            "      name        "+
            "      description        "+
            "      args {        "+
            "        ...InputValue        "+
            "      }        "+
            "      type {        "+
            "        ...TypeRef        "+
            "      }        "+
            "      isDeprecated        "+
            "      deprecationReason        "+
            "    }        "+
            "    inputFields {        "+
            "      ...InputValue        "+
            "    }        "+
            "    interfaces {        "+
            "      ...TypeRef        "+
            "   }        "+
            "    enumValues {        "+
            "      name        "+
            "      description        "+
            "      isDeprecated        "+
            "      deprecationReason        "+
            "    }        "+
            "    possibleTypes {        "+
            "      ...TypeRef        "+
            "    }        "+
            "  }        "+
            "  fragment InputValue on __InputValue {        "+
            "    name        "+
            "    description        "+
            "    type { ...TypeRef }        "+
            "    defaultValue        "+
            "  }        "+
            "  fragment TypeRef on __Type {        "+
            "    kind        "+
            "    name        "+
            "    ofType {        "+
            "      kind        "+
            "      name        "+
            "      ofType {        "+
            "        kind        "+
            "        name        "+
            "        ofType {        "+
            "          kind        "+
            "          name        "+
            "        }        "+
            "      }        "+
            "    }        "+
            "  }        ";



    public String exportSchema() {
        Map<String, Object> result = exec(query, Collections.emptyMap());
        Map<String,Object> ut = new HashMap<>();
        ut.put("data", result);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String out = gson.toJson(ut);
        System.out.println(out);
        return out;
    }

}
