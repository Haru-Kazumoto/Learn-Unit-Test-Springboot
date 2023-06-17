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
    public Student getStudentById(int idStudent) {
        return repository
                .findById(idStudent)
                .orElseThrow(() -> new NoSuchElementException(String.format("Id %s is not exists", idStudent)));
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
    public void deleteStudentById(int id) {
        Student res = repository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("Id %s is not found.", id))
                );
        repository.delete(res);
    }
}
