package ru.hogwarts.school.controller;

import liquibase.pro.packaged.S;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Возможность получить количество всех студентов в школе. Эндпоинт должен вернуть число
    @GetMapping("/total-number")
    public Integer getTotalNumberOfStudents() {
        return studentService.getTotalNumber();
    }

    // Возможность получить средний возраст студентов. Эндпоинт должен вернуть число.
    @GetMapping("/ave-age")
    public Double getAverageAgeOfStudents() {
        return studentService.getAverageAge();
    }

    //Возможность получать только пять последних студентов. Последние студенты считаются теми, у кого идентификатор больше других.
    @GetMapping("last-five")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFive();
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        Student foundStudent = studentService.getStudent(studentId);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping("/age/{studentAge}")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable Integer studentAge) {
        Collection<Student> result = studentService.getStudentsByAge(studentAge);
        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = studentService.addStudent(student);
        return ResponseEntity.ok(addedStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editedStudent = studentService.editStudent(student);
        if (editedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(editedStudent);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<?> removeStudent(@PathVariable Long studentId) {
        studentService.removeStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age-between")
    public Collection<Student> findByAgeBetween(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return studentService.getStudentsByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/faculty/{studentId}")
    public ResponseEntity<?> getStudentsFaculty(@PathVariable Long studentId) {
        Faculty f = studentService.getFaculty(studentId);
        if (f == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(f);
    }

    @GetMapping("/starts-with/{letter}")
    public Collection<String> getByFirstLetter(@PathVariable("letter") String letter) {
        return studentService.getByFirstLetter(letter);
    }

    @GetMapping("/ave-age-2")
    public Double getAverageAge() {
        return studentService.getAverageAgeAgain();
    }


    @GetMapping("/names-thread")
    public Collection<String> getThreadedNames() {
        return studentService.getThreadedNames();
    }


    @GetMapping("/manes-sync")
    public Collection<String> getThreadedNamesSync() {
        return studentService.getThreadedNamesSync();
    }
}
