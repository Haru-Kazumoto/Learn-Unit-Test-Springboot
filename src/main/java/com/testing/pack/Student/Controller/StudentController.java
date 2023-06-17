package com.testing.pack.Student.Controller;

import com.testing.pack.Exception.ValidationErrorException;
import com.testing.pack.Response.ErrorResponse;
import com.testing.pack.Student.Dto.StudentCreateInput;
import com.testing.pack.Student.Model.Student;
import com.testing.pack.Student.Service.Interfaces.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createStudent(
            @RequestBody StudentCreateInput dto,
            BindingResult bindingResult,
            HttpServletResponse response
            ){

        if(bindingResult.hasErrors()){
            response.setStatus(403);
            ErrorResponse res = new ErrorResponse(
                    response.getStatus(),
                    bindingResult.getAllErrors()
            );
            throw new ValidationErrorException(res);
        }

        Student mapEntity = modelMapper.map(dto, Student.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.saveStudent(mapEntity));
    }

    @GetMapping(path = "/get-all")
    public ResponseEntity<?> getAllStudent(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.getAllStudent());
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getStudentById(
            @PathVariable("id") int id
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.getStudentById(id));
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateStudentById(
            @PathVariable("id") int id,
            @RequestBody StudentCreateInput dto,
            BindingResult bindingResult,
            HttpServletResponse response
    ){
        if(bindingResult.hasErrors()){
            response.setStatus(403);
            ErrorResponse res = new ErrorResponse(
                    response.getStatus(),
                    bindingResult.getAllErrors()
            );
            throw new ValidationErrorException(res);
        }

        Student mapEntity = modelMapper.map(dto, Student.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.updateStudentById(mapEntity, id));
    }

}
