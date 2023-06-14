package com.testing.pack.Student.Repository;

import com.testing.pack.Student.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Boolean findStudentByStudentIds(String studentIds);

}
