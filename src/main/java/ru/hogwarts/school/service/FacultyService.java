package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.debug("Method addFaculty is called with faculty = {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        logger.debug("Method getFaculty is called with id = {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Method editFaculty is called with faculty = {}", faculty);
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(long id) {
        logger.debug("Method removeFaculty is called with id = {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.debug("Method getFacultiesByColor is called with color = {}", color);
        return facultyRepository.findByColorLike(color);
    }

    public Collection<Faculty> getAll() {
        logger.debug("Method getAll is called");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultiesByColorOrName(String param) {
        logger.debug("Method getFacultiesByColorOrName is called with param = {}", param);
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(param, param);
    }

    public Collection<Student> getStudents(Long facultyId) {
        logger.debug("Method getStudents is called with id = {}", facultyId);
        return studentRepository.findAll().stream()
                .filter(s -> s.getFaculty().getId() == facultyId)
                .collect(Collectors.toList());
    }

    public String getLongestName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .sorted(Comparator.comparing(String::length, Comparator.reverseOrder()))
//                .findFirst().toString();
                .findFirst().get();
    }


    public Integer getStrange() {
        return Stream.iterate(1, a -> a + 1)
//                .parallel()
                .limit(1_000_000)
                .reduce(0, Integer::sum);
    }
}
