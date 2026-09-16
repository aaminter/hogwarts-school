package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

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
        return studentRepository.findAll(pageable).getContent();
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for find students by age = {}", age);
        return studentRepository.findByAge(age);
    }

    public Integer getStudentsCount() {
        logger.info("Was invoked method for get count of students");
        return studentRepository.getCountOfStudents();
    }

    public Double getAverageAge() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.findLastFiveStudents();
    }

    public List<String> getAllStudentsStartingWithA() {
        logger.info("Was invoked method for getting students starting with A");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name != null && name.toUpperCase().startsWith("А"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    public Double getAverageAgeWithStream() {
        logger.info("Was invoked method for getting average age with stream");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }
    public void printStudentsThreads() {
        List<Student> students = studentRepository.findAll();
        if (students.size() < 6) {
            logger.warn("Need at least 6 students in DB to demonstrate threads!");
            return;
        }

        // Главный поток (первые 2 студента)
        logger.info("Main thread: {}", students.get(0).getName());
        logger.info("Main thread: {}", students.get(1).getName());

        // Параллельный поток 1 (3-й и 4-й студенты)
        new Thread(() -> {
            logger.info("Parallel Thread 1: {}", students.get(2).getName());
            logger.info("Parallel Thread 1: {}", students.get(3).getName());
        }).start();

        // Параллельный поток 2 (5-й и 6-й студенты)
        new Thread(() -> {
            logger.info("Parallel Thread 2: {}", students.get(4).getName());
            logger.info("Parallel Thread 2: {}", students.get(5).getName());
        }).start();
    }

    public void printStudentsSynchronized() {
        List<Student> students = studentRepository.findAll();
        if (students.size() < 6) {
            logger.warn("Need at least 6 students in DB to demonstrate threads!");
            return;
        }

        // Главный поток
        printStudentSync(students.get(0));
        printStudentSync(students.get(1));

        // Поток 1
        new Thread(() -> {
            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        }).start();

        // Поток 2
        new Thread(() -> {
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        }).start();
    }

    private synchronized void printStudentSync(Student student) {
        logger.info("Synchronized Thread [{}]: {}", Thread.currentThread().getName(), student.getName());
    }
}