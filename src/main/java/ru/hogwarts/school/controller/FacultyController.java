package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping
    public List<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/color")
    public Collection<Faculty> findByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping("/search")
    public Collection<Faculty> findFaculty(@RequestParam String value) {
        return facultyService.findByNameOrColor(value);
    }
    @GetMapping("/{id}/students")
    public Collection<Student> getStudents(@PathVariable Long id) {
        return facultyService.getStudents(id);
    }
}