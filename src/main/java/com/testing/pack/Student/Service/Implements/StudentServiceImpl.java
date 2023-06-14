package com.testing.pack.Student.Service.Implements;

import com.testing.pack.Response.PayloadResponse;
import com.testing.pack.Student.Model.Student;
import com.testing.pack.Student.Repository.StudentRepository;
import com.testing.pack.Student.Service.Interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public Student saveStudent(Student body) {
        boolean isStudentIdsExists = repository.findStudentByStudentIds(body.getStudentIds());
        if(isStudentIdsExists){
            throw new DataIntegrityViolationException(String.format("Student ids %s is already exists", body.getStudentIds()));
        }
        return repository.save(body);
    }

    @Override
    public List<Student> getAllStudent() {
        return repository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(int idStudent) {
        var isIdExists = repository.findById(idStudent);
        if (isIdExists.isEmpty()){
            throw new NoSuchElementException("Id isn't exists");
        }
        return isIdExists;
    }

    @Override
    public Student updateStudentById(Student body, int idStudent) {
        Optional<Student> id = repository.findById(idStudent);
        if (id.isEmpty()){
            throw new NoSuchElementException(String.format("Id %s is not found.", idStudent));
        }
        return repository.save(body);
    }

    @Override
    public PayloadResponse deleteStudentById(int id) {
        Optional<Student> idStudent = repository.findById(id);
        if(idStudent.isEmpty()) throw new NoSuchElementException(String.format("Id %s is not found.", id));
        repository.deleteById(id);
        return new PayloadResponse(
                HttpStatus.OK.value(),
                String.format("Id %s of student has deleted",id),
                new Date(),
                null
        );
    }
}
