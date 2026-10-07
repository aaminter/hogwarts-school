package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    void findFaculties() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red", null);

        when(facultyService.findByNameOrColor(any())).thenReturn(List.of(faculty));

        mockMvc.perform(
                get("/faculty/search")
                        .param("value", "Gryffindor")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Gryffindor"));
    }

    @Test
    void getFacultyWhenFacultyDoesNotExist() throws Exception {
        when(facultyService.findFaculty(999L)).thenThrow(new FacultyNotFoundException(999L));

        mockMvc.perform(get("/faculty/999"))
                .andExpect(status().isNotFound());
    }
}
