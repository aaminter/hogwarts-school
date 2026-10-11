package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student with id = {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {

        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student with id = {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public List<Student> getAllStudents(Pageable pageable) {
        logger.info("Was invoked method for get all students with pagination");
        return studentRepository.findAll(pageable).getContent();
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for find students by age = {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);

        if (student == null) {
            return null;
        }

        return student.getFaculty();
    }

    public List<String> getNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageAge() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public long getSum() {
        return java.util.stream.LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }
}