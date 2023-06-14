package com.testing.pack;

import com.testing.pack.Student.Model.Student;
import com.testing.pack.Student.Repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestStudentManagementRepository {

    @Autowired
    private StudentRepository studentRepository;

    List<Student> students = new ArrayList<>();

    //This is arrange the data
    @BeforeEach
    void setUp() {
        Student student1 = Student.builder()
                .firstname("Joe")
                .lastname("Doe")
                .age(18)
                .studentIds(generateNumberForStudentIds())
                .build();

        Student student2 = Student.builder()
                .firstname("Bilal")
                .lastname("Ahmed")
                .age(17)
                .studentIds(generateNumberForStudentIds())
                .build();

        Student student3 = Student.builder()
                .firstname("Kevin")
                .lastname("Lacoste")
                .age(18)
                .studentIds(generateNumberForStudentIds())
                .build();

        Student student4 = Student.builder()
                .firstname("Navasa")
                .lastname("Salsabila")
                .age(18)
                .studentIds(generateNumberForStudentIds())
                .build();

        Student student5 = Student.builder()
                .firstname("Haru")
                .lastname("Kazumoto")
                .age(18)
                .studentIds(generateNumberForStudentIds())
                .build();

        students.addAll(Arrays.asList(
                student1,
                student2,
                student3,
                student4,
                student5
        ));
    }

    @Test
    void shouldSaveTheRecord() {

        //Act
        List<Student> savedStudent = studentRepository.saveAll(students);

        //Assert
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.size()).isEqualTo(5);
    }

    @Test
    void shouldErrorWhenStudentIdsIsDuplicate() {
        //Arrange
        List<Student> students = new ArrayList<>();

        Student student1 = Student.builder()
                .firstname("Joe")
                .lastname("Doe")
                .age(18)
                .studentIds("123456789012345")
                .build();

        Student student2 = Student.builder()
                .firstname("Bilal")
                .lastname("Ahmed")
                .age(17)
                .studentIds("123456789012345")
                .build();

        students.add(student1);
        students.add(student2);

        //Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            studentRepository.saveAll(students);
        });
    }

    @Test
    void shouldGetAllStudentRecords() {

        //Act
        studentRepository.saveAll(students);
        students = studentRepository.findAll();

        //Assert
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(5);
    }

    @Test
    void shouldReturnAStudentRecordById() {
        //Arrange
        Optional<Student> idStudent = Optional.empty();
        for(Student student : students){
            idStudent = studentRepository.findById(student.getId());
        }

        //Act
        studentRepository.saveAll(students);

        //Assert
        assertThat(idStudent.isPresent()).isNotNull();
    }

    @Test
    void shouldGenerateRandomStringNumber15Char(){
        String randomString = generateNumberForStudentIds(); //should create 15 char

        assertEquals(15,randomString.length());
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
