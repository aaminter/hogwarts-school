package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void getAllStudentsShouldReturnOk() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/student",
                String.class
        );

        assertEquals(200, response.getStatusCode().value());
    }
}