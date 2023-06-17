package com.testing.pack;

import com.testing.pack.Student.Model.Student;
import com.testing.pack.Student.Repository.StudentRepository;
import com.testing.pack.Student.Service.Implements.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

//Untuk pengetesan per kelas gunakan command mvn test -Dtest=TestStudentManagementServices
@ExtendWith(MockitoExtension.class)
public class TestStudentManagementServices {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    Student student = new Student();

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .id(1)
                .firstname("Navasa")
                .lastname("Salsabila Putri")
                .age(18)
                .studentIds(generateNumberForStudentIds())
                .build();
    }

    @Test
    void shouldCreateStudentRecord() {
        //Act
        Mockito.when(studentRepository.save(
                Mockito.any(Student.class)
        )).thenAnswer(invocation -> {
            Student student = invocation.getArgument(0);
//            System.out.println("Saved student : " + student.getFirstname());
            return student;
        });

        Student savedStudentRecord = studentService.saveStudent(student);

        //Assert
        assertThat(savedStudentRecord).isNotNull();
    }

    @Test
    void shouldGetAllDataStudent() {

        List<Student> getAllStudentData = studentRepository.findAll();
        Mockito.when(getAllStudentData).thenReturn(List.of(student));

        List<Student> studentData = studentService.getAllStudent();

        assertThat(studentData).isNotNull();
    }

    @Test
    void shouldGetStudentById() {
        int studentId = 1;

        //Act
        Mockito
                .when(studentRepository.findById(studentId))
                .thenReturn(Optional.ofNullable(student));

        Student studentData = studentService.getStudentById(studentId);

        //Assert
        assertThat(studentData).isNotNull();
    }

    @Test
    void shouldUpdateStudentById() {
        int studentId = 1;

        Student updatedStudent = Student.builder()
                .id(1)
                .firstname("Updated")
                .lastname("Updated")
                .age(17)
                .studentIds(generateNumberForStudentIds())
                .build();

        Mockito
                .when(studentRepository.findById(studentId))
                .thenReturn(Optional.ofNullable(student));

        Mockito
                .when(studentRepository.save(student))
                .thenReturn(student);

        Student updatedData = studentService.updateStudentById(student, studentId);

        assertThat(updatedData).isNotNull();
    }

    @Test
    void shouldDeleteStudentRecordById() {
        int studentIdWantToDelete = 1;

        studentRepository.save(student);

        Mockito
                .when(studentRepository.findById(studentIdWantToDelete))
                .thenReturn(Optional.ofNullable(student));
        Mockito
                .doNothing()
                .when(studentRepository)
                .delete(student);

        assertAll(() -> studentService.deleteStudentById(studentIdWantToDelete));
    }

    public String generateNumberForStudentIds(){

        String ALLOWED_CHAR = "1234567890";

        Random randomNumber = new Random();
        StringBuilder builder = new StringBuilder(15);

        for (int i = 0; i < 15; i++) {
            int randomIndex =randomNumber.nextInt(ALLOWED_CHAR.length());
            char randomChar = ALLOWED_CHAR.charAt(randomIndex);
            builder.append(randomChar);
        }

        return builder.toString();
    }
}
