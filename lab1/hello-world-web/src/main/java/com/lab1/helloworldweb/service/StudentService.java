package com.lab1.helloworldweb.service;

import com.lab1.helloworldweb.dto.StudentRequestDto;
import com.lab1.helloworldweb.dto.StudentResponseDto;
import com.lab1.helloworldweb.exception.StudentNotFoundException;
import com.lab1.helloworldweb.model.Student;
import com.lab1.helloworldweb.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        Student student = new Student(
            null,
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getEmail(),
            requestDto.getDateOfBirth(),
            requestDto.getMajor(),
            requestDto.getGpa()
        );

        Student savedStudent = studentRepository.save(student);
        return StudentResponseDto.fromStudent(savedStudent);
    }

    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException(id));
        return StudentResponseDto.fromStudent(student);
    }

    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream()
            .map(StudentResponseDto::fromStudent)
            .collect(Collectors.toList());
    }

    public List<StudentResponseDto> getStudentsByMajor(String major) {
        return studentRepository.findByMajor(major).stream()
            .map(StudentResponseDto::fromStudent)
            .collect(Collectors.toList());
    }

    public List<StudentResponseDto> getStudentsByMinGpa(Double minGpa) {
        return studentRepository.findByGpaGreaterThanEqual(minGpa).stream()
            .map(StudentResponseDto::fromStudent)
            .collect(Collectors.toList());
    }

    public StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto) {
        Student existingStudent = studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException(id));

        existingStudent.setFirstName(requestDto.getFirstName());
        existingStudent.setLastName(requestDto.getLastName());
        existingStudent.setEmail(requestDto.getEmail());
        existingStudent.setDateOfBirth(requestDto.getDateOfBirth());
        existingStudent.setMajor(requestDto.getMajor());
        existingStudent.setGpa(requestDto.getGpa());

        Student updatedStudent = studentRepository.save(existingStudent);
        return StudentResponseDto.fromStudent(updatedStudent);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }
}

