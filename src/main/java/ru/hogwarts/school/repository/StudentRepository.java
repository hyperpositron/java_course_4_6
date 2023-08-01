package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeLessThan(int age);

    Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge);


    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer getTotalNumber();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Double getAverageAge();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    Collection<Student> getLastFive();
}
