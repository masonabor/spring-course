package com.lab1.helloworldweb.repository;

import com.lab1.helloworldweb.model.Student;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {
    
    private final Map<Long, Student> students = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public StudentRepository() {
        // Initialize with some sample data
        save(new Student(null, "Andriy", "Kovalenko", "andriy.kovalenko@example.com", 
                        LocalDate.of(2002, 5, 15), "Computer Science", 3.8));
        save(new Student(null, "Tania", "Shevchenko", "tania.shevchenko@example.com", 
                        LocalDate.of(2003, 8, 22), "Mathematics", 3.9));
        save(new Student(null, "Yurii", "Melnyk", "yurii.melnyk@example.com", 
                        LocalDate.of(2002, 3, 10), "Physics", 3.7));
        save(new Student(null, "Olena", "Bondar", "olena.bondar@example.com", 
                        LocalDate.of(2003, 11, 5), "Computer Science", 3.6));
    }

    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(idGenerator.getAndIncrement());
        }
        students.put(student.getId(), student);
        return student;
    }

    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(students.get(id));
    }

    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    public List<Student> findByMajor(String major) {
        return students.values().stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    public List<Student> findByGpaGreaterThanEqual(Double gpa) {
        return students.values().stream()
                .filter(s -> s.getGpa() >= gpa)
                .collect(Collectors.toList());
    }

    public boolean existsById(Long id) {
        return students.containsKey(id);
    }

    public void deleteById(Long id) {
        students.remove(id);
    }

    public long count() {
        return students.size();
    }
}

