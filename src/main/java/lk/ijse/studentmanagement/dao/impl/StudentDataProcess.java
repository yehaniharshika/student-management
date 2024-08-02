package lk.ijse.studentmanagement.dao.impl;

import lk.ijse.studentmanagement.dao.StudentData;
import lk.ijse.studentmanagement.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentDataProcess implements StudentData {
    static  String SAVE_STUDENT = "INSERT INTO student(id,name,city,email,level) VALUES(?,?,?,?,?);";
    static  String GET_STUDENT = "SELECT * FROM student WHERE id=?";
    static  String UPDATE_STUDENT = "UPDATE student SET name=?,city=?,email=?,level=? WHERE id=?";
    static  String DELETE_STUDENT = "DELETE FROM student WHERE id=?";

    @Override
    public StudentDTO getStudent(String studentId, Connection connection) {
        var studentDTO = new StudentDTO();
        try {
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, studentId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                studentDTO.setId(resultSet.getString("id"));
                studentDTO.setName(resultSet.getString("name"));
                studentDTO.setCity(resultSet.getString("city"));
                studentDTO.setEmail(resultSet.getString("email"));
                studentDTO.setLevel(resultSet.getString("level"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentDTO;
    }


    @Override
    public boolean saveStudent(StudentDTO studentDTO, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1,studentDTO.getId());
            ps.setString(2,studentDTO.getName());
            ps.setString(3,studentDTO.getCity());
            ps.setString(4,studentDTO.getEmail());
            ps.setString(5,studentDTO.getLevel());

            /*if (ps.executeUpdate() != 0){
                return "save student successfully!!";
            }else {
                return "save student failed";
            }*/
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteStudent(String studentId, Connection connection) {
        try {
            var ps = connection.prepareStatement(DELETE_STUDENT);
            ps.setString(1, studentId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateStudent(String studentId, StudentDTO updateStudent, Connection connection) {

        try {
            var ps = connection.prepareStatement(UPDATE_STUDENT);
            ps.setString(1, updateStudent.getName());
            ps.setString(2, updateStudent.getCity());
            ps.setString(3, updateStudent.getEmail());
            ps.setString(4, updateStudent.getLevel());
            ps.setString(5, studentId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
