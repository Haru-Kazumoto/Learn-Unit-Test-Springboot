package com.testing.pack.Student.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateInput {

    @NotNull(message = "Firstname cannot be null!")
    @NotEmpty(message = "Firstname cannot be empty!")
    private String firstname;

    @NotNull(message = "Lastname cannot be null, at least empty!")
    private String lastname;

    @NotNull(message = "Age cannot be null!")
    @NotEmpty(message = "Age cannot be empty!")
    @Max(
            value = 18,
            message = "Maximum of student age is 18!"
    )
    @Min(
            value = 15,
            message = "The lowest age of the student is 15!"
    )
    private int age;

    @NotNull(message = "Student id cannot be null!")
    @NotEmpty(message = "Student id cannot be empty!")
    @Max(
            value = 15,
            message = "Student id only have 15 char"
    )
    private String studentIds;

}
