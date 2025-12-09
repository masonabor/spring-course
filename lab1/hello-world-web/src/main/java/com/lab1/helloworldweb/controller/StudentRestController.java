package com.lab1.helloworldweb.controller;

import com.lab1.helloworldweb.dto.StudentRequestDto;
import com.lab1.helloworldweb.dto.StudentResponseDto;
import com.lab1.helloworldweb.service.StudentService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * CREATE - Create a new student
     * POST /api/students
     * Returns: 201 Created with the created student
     */
    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto requestDto) {
        StudentResponseDto createdStudent = studentService.createStudent(requestDto);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    /**
     * READ - Get a student by ID
     * GET /api/students/{id}
     * Returns: 200 OK with the student, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        StudentResponseDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    /**
     * READ - Get all students with optional filtering
     * GET /api/students
     * Query params: major, minGpa
     * Returns: 200 OK with list of students
     */
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents(
            @RequestParam(required = false) String major,
            @RequestParam(required = false) Double minGpa) {

        List<StudentResponseDto> students;

        if (major != null) {
            students = studentService.getStudentsByMajor(major);
        } else if (minGpa != null) {
            students = studentService.getStudentsByMinGpa(minGpa);
        } else {
            students = studentService.getAllStudents();
        }

        return ResponseEntity.ok(students);
    }

    /**
     * UPDATE - Update an existing student (full update)
     * PUT /api/students/{id}
     * Returns: 200 OK with the updated student, or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto requestDto) {
        StudentResponseDto updatedStudent = studentService.updateStudent(id, requestDto);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * DELETE - Delete a student by ID
     * DELETE /api/students/{id}
     * Returns: 204 No Content, or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}

