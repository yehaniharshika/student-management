package lk.ijse.studentmanagement.dao;

import lk.ijse.studentmanagement.dto.StudentDTO;

import java.sql.Connection;

public interface StudentData {
    StudentDTO getStudent(String studentId, Connection connection);
    boolean saveStudent(StudentDTO studentDTO, Connection connection);
    boolean deleteStudent(String studentId,Connection connection);
    boolean updateStudent(String studentId,StudentDTO updateStudent,Connection connection);

}
