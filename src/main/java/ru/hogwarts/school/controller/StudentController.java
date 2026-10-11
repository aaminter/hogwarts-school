
package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Collection;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/student")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping("/student")
    public Student editStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/student")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/student/age")
    public Collection<Student> findByAgeBetween(
            @RequestParam int min,
            @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/student/{id}/faculty")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @GetMapping("/student/names")
    public List<String> getNamesStartingWithA() {
        return studentService.getNamesStartingWithA();
    }

    @GetMapping("/student/average-age")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/student/sum")
    public long getSum() {
        return studentService.getSum();
    }

    @GetMapping("/students/print-parallel")
    public void printParallel() {
        List<Student> students = studentService.getAllStudents();

        if (students.size() > 0) {
            System.out.println(students.get(0).getName());
        }
        if (students.size() > 1) {
            System.out.println(students.get(1).getName());
        }

        Thread thread1 = new Thread(() -> {
            if (students.size() > 2) {
                System.out.println(students.get(2).getName());
            }
            if (students.size() > 3) {
                System.out.println(students.get(3).getName());
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            if (students.size() > 4) {
                System.out.println(students.get(4).getName());
            }
            if (students.size() > 5) {
                System.out.println(students.get(5).getName());
            }
        }, "thread2");

        thread1.start();
        thread2.start();
    }

    @GetMapping("/students/print-synchronized")
    public void printSynchronized() {
        List<Student> students = studentService.getAllStudents();

        if (students.size() > 0) {
            printName(students.get(0).getName());
        }
        if (students.size() > 1) {
            printName(students.get(1).getName());
        }

        Thread thread1 = new Thread(() -> {
            if (students.size() > 2) {
                printName(students.get(2).getName());
            }
            if (students.size() > 3) {
                printName(students.get(3).getName());
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            if (students.size() > 4) {
                printName(students.get(4).getName());
            }
            if (students.size() > 5) {
                printName(students.get(5).getName());
            }
        }, "thread2");

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void printName(String name) {
        System.out.println(name);
    }
}
