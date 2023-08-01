package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.debug("Method addStudent is called with student = {}", student);
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        logger.debug("Method getStudent is called with id = {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.debug("Method editStudent is called with student = {}", student);
        return studentRepository.save(student);
    }

    public void removeStudent(long id) {
        logger.debug("Method removeStudent is called with id = {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.debug("Method getStudentByAge is called with age = {}", age);
        return studentRepository.findByAgeLessThan(age);
    }

    public Collection<Student> getAll() {
        logger.debug("Method getAll is called");
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        logger.debug("Method getStudentsByAgeBetween is called with minAge = {} and maxAge = {}", minAge, maxAge);
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

    public Faculty getFaculty(long id) {
        logger.debug("Method getFaculty is called with id = {}", id);
        Student s =  studentRepository.findById(id).orElse(null);
        if (s == null) {
            return null;
        }
        return s.getFaculty();
    }

    // Возможность получить количество всех студентов в школе.
    public Integer getTotalNumber() {
        logger.debug("Method getTotalNumber is called");
        return studentRepository.getTotalNumber();
    }

    public Double getAverageAge() {
        logger.debug("Method getAverageAge is called");
        return studentRepository.getAverageAge();
    }

    public Collection<Student> getLastFive() {
        logger.debug("Method getLastFive is called");
        return studentRepository.getLastFive();
    }

    public Collection<String> getByFirstLetter(String letter) {
        return studentRepository.findAll().stream()
                //.filter(student -> student.getName().charAt(0) == letter)
                .map(Student::getName)
                //.filter(name -> name.startsWith(letter.toUpperCase()))
                .filter(name -> name.substring(0, 1).equalsIgnoreCase(letter))
                .sorted()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public Double getAverageAgeAgain() {
        return studentRepository.findAll().stream()
//                .mapToInt(Student::getAge)
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0.0);
    }

    public Collection<String> getThreadedNames() {
        List<String> sixStudents = studentRepository.findAll().stream()
                .limit(6)
                .map(Student::getName)
                .collect(Collectors.toList());

        System.out.println(sixStudents.get(0));
        System.out.println(sixStudents.get(1));

        new Thread(() -> {
            try{
                System.out.println(sixStudents.get(2));
                Thread.sleep(500);
                System.out.println(sixStudents.get(3));
            } catch (InterruptedException e) {}
        }).start();

        new Thread(() -> {
            try{
                System.out.println(sixStudents.get(4));
                Thread.sleep(500);
                System.out.println(sixStudents.get(5));
            } catch (InterruptedException e) {}
        }).start();

        return sixStudents;
    }


    private synchronized void printStudent(String name1, String name2) {
        try {
            System.out.println(name1);
            Thread.sleep(500);
            System.out.println(name2);
        } catch (InterruptedException e) {}
    }

    public Collection<String> getThreadedNamesSync() {
        List<String> sixStudents = studentRepository.findAll().stream()
                .limit(6)
                .map(Student::getName)
                .collect(Collectors.toList());

        printStudent(sixStudents.get(0), sixStudents.get(1));

        new Thread(() -> {
            printStudent(sixStudents.get(2), sixStudents.get(3));
        }).start();

        new Thread(() -> {
            printStudent(sixStudents.get(4), sixStudents.get(5));
        }).start();

        return sixStudents;
    }
}
