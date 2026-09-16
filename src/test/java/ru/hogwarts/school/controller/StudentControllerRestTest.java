package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class StudentControllerRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void createStudent() {
        Student student = new Student(null, "Harry Potter", 17, null);

        ResponseEntity<Student> response = restTemplate.postForEntity(
                url("/student"),
                student,
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Harry Potter", response.getBody().getName());
        assertEquals(17, response.getBody().getAge());
    }

    @Test
    void getStudent() {
        Student student = new Student(null, "Harry Potter", 17, null);

        ResponseEntity<Student> created = restTemplate.postForEntity(
                url("/student"),
                student,
                Student.class
        );

        assertEquals(HttpStatus.OK, created.getStatusCode());
        assertNotNull(created.getBody());

        Long id = created.getBody().getId();

        ResponseEntity<Student> response = restTemplate.getForEntity(
                url("/student/" + id),
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Harry Potter", response.getBody().getName());
    }

    @Test
    void editStudent() {
        Student student = new Student(null, "Harry Potter", 17, null);

        ResponseEntity<Student> created = restTemplate.postForEntity(
                url("/student"),
                student,
                Student.class
        );

        assertNotNull(created.getBody());

        Student updatedStudent = created.getBody();
        updatedStudent.setName("Harry Potter Updated");
        updatedStudent.setAge(18);

        HttpEntity<Student> request = new HttpEntity<>(updatedStudent);

        ResponseEntity<Student> response = restTemplate.exchange(
                url("/student"),
                HttpMethod.PUT,
                request,
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Harry Potter Updated", response.getBody().getName());
        assertEquals(18, response.getBody().getAge());
    }

    @Test
    void deleteStudent() {
        Student student = new Student(null, "Harry Potter", 17, null);

        ResponseEntity<Student> created = restTemplate.postForEntity(
                url("/student"),
                student,
                Student.class
        );

        assertNotNull(created.getBody());

        Long id = created.getBody().getId();

        ResponseEntity<Void> response = restTemplate.exchange(
                url("/student/" + id),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllStudents() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                url("/student"),
                Student[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void findStudentsByAgeBetween() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                url("/student/age?min=10&max=20"),
                Student[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void unknownStudentEndpointReturns404() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/student/unknown/endpoint"),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}




