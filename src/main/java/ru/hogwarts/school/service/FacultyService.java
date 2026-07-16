package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.Collection;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        return faculty.orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByNameOrColor(String value) {
        return facultyRepository
                .findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(value, value);
    }

    public Collection<Student> getStudents(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);

        if (faculty == null) {
            return null;
        }

        return faculty.getStudents();
    }
}