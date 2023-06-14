package com.testing.pack;

import com.testing.pack.Student.Model.Student;
import com.testing.pack.Student.Repository.StudentRepository;
import com.testing.pack.Student.Service.Implements.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TestStudentManagementServices {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void shouldCreateStudentRecord() {
        //Arrange
        Student studentRecord = Student.builder()
                .firstname("Jhoe")
                .lastname("Doe")
                .age(18)
                .studentIds(generateNumberForStudentIds())
                .build();

        //Act
        Mockito.when(studentRepository.save(
                Mockito.any(Student.class)
        )).thenReturn(studentRecord);

        Student savedStudentRecord = studentService.saveStudent(studentRecord);

        //Assert
        assertThat(savedStudentRecord).isNotNull();
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
