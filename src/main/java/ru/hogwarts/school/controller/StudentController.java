package ru.hogwarts.school.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents(@RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            return ResponseEntity.ok(studentService.getAllStudents(PageRequest.of(page, size)));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/count")
    public Integer getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @GetMapping("/average-age")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/last-five")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/all-by-letter-a")
    public ResponseEntity<List<String>> getAllStudentsStartingWithA() {
        return ResponseEntity.ok(studentService.getAllStudentsStartingWithA());
    }

    @GetMapping("/average-age-stream")
    public ResponseEntity<Double> getAverageAgeWithStream() {
        return ResponseEntity.ok(studentService.getAverageAgeWithStream());
    }
    @GetMapping("/print-parallel")
    public ResponseEntity<Void> printStudentsParallel() {
        studentService.printStudentsThreads();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/print-synchronized")
    public ResponseEntity<Void> printStudentsSynchronized() {
        studentService.printStudentsSynchronized();
        return ResponseEntity.ok().build();
    }
}