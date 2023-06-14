package com.testing.pack.Student.Service.Interfaces;

import com.testing.pack.Response.PayloadResponse;
import com.testing.pack.Student.Model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student saveStudent(Student body);
    List<Student> getAllStudent();
    Optional<Student> getStudentById(int id);
    Student updateStudentById(Student body, int id);
    PayloadResponse deleteStudentById(int id);

}
