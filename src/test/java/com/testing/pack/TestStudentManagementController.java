package com.testing.pack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.pack.Student.Controller.StudentController;
import com.testing.pack.Student.Dto.StudentCreateInput;
import com.testing.pack.Student.Model.Student;
import com.testing.pack.Student.Service.Interfaces.StudentService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TestStudentManagementController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    StudentCreateInput student = new StudentCreateInput();

    @BeforeEach
    void setUp() {
        student = StudentCreateInput.builder()
                .firstname("Navasa")
                .lastname("Salsabila Putri")
                .age(18)
                .studentIds(generateNumberForStudentIds())
                .build();
    }

    @Test
    void shouldCreateStudent() throws Exception{
        given(
                studentService.saveStudent(ArgumentMatchers.any())
        ).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.firstname",
                        CoreMatchers.is("Navasa"))
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.lastname", //Field
                        CoreMatchers.is("Salsabila Putri") //Expected value
                ));
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldGetAllStudentRecord() throws Exception {
        // Mock data
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1,"Navasa", "Salsabila Putri", 18, generateNumberForStudentIds()));
        studentList.add(new Student(2,"Ziaurrahman", "Athaya", 17, generateNumberForStudentIds()));

        // Mock service method
        when(studentService.getAllStudent()).thenReturn(studentList);

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/student/get-all")
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.length()",
                        CoreMatchers.is(2))
                );
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldGetStudentById() throws Exception {
        int studentId = 1;

        Student student = new Student(
                1,
                "Navasa",
                "Salsabila",
                18,
                generateNumberForStudentIds()
        );

        when(studentService.getStudentById(studentId)).thenReturn(student);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(String.format("/student/get/%s",studentId))
                .content(objectMapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath(
                        "$.id",
                        CoreMatchers.is(1)
                ));
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldUpdateTheStudentRecordById() {

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
