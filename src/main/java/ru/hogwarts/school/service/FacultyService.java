package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty with id = {}", id);

        Faculty faculty = facultyRepository.findById(id).orElse(null);

        if (faculty == null) {
            logger.error("There is no faculty with id = {}", id);
            throw new FacultyNotFoundException(id);
        }

        logger.debug("Faculty with id = {} was found", id);
        return faculty;
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty with id = {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for find faculties by color = {}", color);
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByName(String name) {
        logger.info("Was invoked method for find faculties by name = {}", name);
        logger.debug("Searching faculties with name containing '{}'", name);
        return facultyRepository.findByNameContainingIgnoreCase(name);
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.info("Was invoked method for find faculties by name or color");

        if (name != null && color != null) {
            logger.debug("Searching faculties by name '{}' or color '{}'", name, color);
            return facultyRepository
                    .findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
        }

        if (name != null) {
            return findByName(name);
        }

        if (color != null) {
            return findByColor(color);
        }

        return getAllFaculties();
    }

    public Collection<Faculty> findByNameOrColor(String value) {
        logger.info("Was invoked method for find faculties by name or color with value = {}", value);
        return facultyRepository
                .findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(value, value);
    }

    public Collection<Student> getStudents(Long facultyId) {
        logger.info("Was invoked method for get students for faculty with id = {}", facultyId);

        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);

        if (faculty == null) {
            logger.warn("No faculty with id = {}", facultyId);
            return null;
        }

        return faculty.getStudents();
    }

    public String getLongestFacultyName() {
        logger.info("Was invoked method for get longest faculty name");

        String longestName = facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .filter(name -> name != null)
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        logger.debug("Longest faculty name: {}", longestName);
        return longestName;
    }
}
