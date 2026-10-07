package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

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
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            logger.warn("No student with id = {}", id);
        } else {
            logger.debug("Student with id = {} was found", id);
        }

        return student;
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
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
        logger.debug("Requested page: {}, page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return studentRepository.findAll(pageable).getContent();
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for find students by age = {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find students by age between {} and {}", min, max);
        logger.debug("Searching students in age range from {} to {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(Long studentId) {
        logger.info("Was invoked method for get faculty for student with id = {}", studentId);

        Student student = studentRepository.findById(studentId).orElse(null);

        if (student == null) {
            logger.warn("No student with id = {}", studentId);
            return null;
        }

        return student.getFaculty();
    }
}
