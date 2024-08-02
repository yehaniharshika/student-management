package lk.ijse.studentmanagement.controller;

import jakarta.json.*;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.studentmanagement.dao.impl.StudentDataProcess;
import lk.ijse.studentmanagement.dto.StudentDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/student")
public class StudentController extends HttpServlet {
    Connection connection;
    //me tika StudentDataProcess class ekata aran yanwa
    static  String SAVE_STUDENT = "INSERT INTO student(id,name,city,email,level) VALUES(?,?,?,?,?);";
    static  String GET_STUDENT = "SELECT * FROM student WHERE id=?";
    static  String UPDATE_STUDENT = "UPDATE student SET name=?,city=?,email=?,level=? WHERE id=?";
    static  String DELETE_STUDENT = "DELETE FROM student WHERE id=?";

    @Override
    public void init() throws ServletException {
        try {
            var driverClass = getServletContext().getInitParameter("driver-class");
            var dbUrl = getServletContext().getInitParameter("dbURL");
            var userName = getServletContext().getInitParameter("dbUserName");
            var password = getServletContext().getInitParameter("dbPassword");
            Class.forName(driverClass);
            this.connection = DriverManager.getConnection(dbUrl,userName,password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

   /* @Override
    public ServletContext getServletContext() {
        return super.getServletContext();
    }*/

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //Todo: save student
//        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
//            //send error
//            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
//        }
//
//
//        String id = UUID.randomUUID().toString();
//        //Create json object
//        Jsonb jsonb = JsonbBuilder.create();
//        StudentDTO studentDTO = jsonb.fromJson(req.getReader(),StudentDTO.class);
//        studentDTO.setId(id);
//        System.out.println(studentDTO);
//
//        try {
//            var ps = connection.prepareStatement(SAVE_STUDENT);
//            ps.setString(1,studentDTO.getId());
//            ps.setString(2,studentDTO.getName());
//            ps.setString(3,studentDTO.getCity());
//            ps.setString(4,studentDTO.getEmail());
//            ps.setString(5,studentDTO.getLevel());
//
//            if (ps.executeUpdate() != 0){
//                resp.getWriter().write("student saved");
//            }else {
//                resp.getWriter().write("student not saved");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        /*//process(first)
//        BufferedReader reader = req.getReader();
//        StringBuilder sb = new StringBuilder();
//        var writer = resp.getWriter();
//        reader.lines().forEach(line ->sb.append(line + "\n"));
//        System.out.println(sb);
//        writer.write(sb.toString());
//        writer.close();*/
//
//        //JSON manipulate with parsson library
//        //this is JSON-P process
//        /*JsonReader reader= Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        System.out.println(jsonObject.getString("email"));*/
//
//        //print array
//        /*JsonReader reader = Json.createReader(req.getReader());
//        JsonArray jsonArray = reader.readArray();
//
//        for (int i =0; i< jsonArray.size();i++){
//            JsonObject jsonObject = jsonArray.getJsonObject(i);
//            System.out.println(jsonObject.getString("name"));
//        }*/
//
//
//        /*String id = UUID.randomUUID().toString();
//        //Create json object
//        Jsonb jsonb = JsonbBuilder.create();
//        StudentDTO studentDTO = jsonb.fromJson(req.getReader(),StudentDTO.class);
//        studentDTO.setId(id);
//        System.out.println(studentDTO);*/
//
//        /*Array print jsonb*/
//       /* Jsonb jsonb = JsonbBuilder.create();//json bind type object ekk create krla (entry point ek)
//        List<StudentDTO> studentDTOList = jsonb.fromJson(req.getReader(), new ArrayList<StudentDTO>(){}.getClass().getGenericSuperclass());
//        studentDTOList.forEach(System.out::println);*/
//
//    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: save student
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try(var writer = resp.getWriter()) {
            String id = UUID.randomUUID().toString();
            //Create json object
            Jsonb jsonb = JsonbBuilder.create();
            StudentDTO studentDTO = jsonb.fromJson(req.getReader(),StudentDTO.class);
            studentDTO.setId(id);

            var saveData = new StudentDataProcess();
//            writer.write(saveData.saveStudent(studentDTO,connection));
            if (saveData.saveStudent(studentDTO,connection)){
                writer.write("student saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Save student failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }


        /*//process
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        var writer = resp.getWriter();
        reader.lines().forEach(line ->sb.append(line + "\n"));
        System.out.println(sb);
        writer.write(sb.toString());
        writer.close();*/

        //JSON manipulate with parsson library
        //this is JSON-P process
        /*JsonReader reader= Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        System.out.println(jsonObject.getString("email"));*/

        //print array
        /*JsonReader reader = Json.createReader(req.getReader());
        JsonArray jsonArray = reader.readArray();

        for (int i =0; i< jsonArray.size();i++){
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            System.out.println(jsonObject.getString("name"));
        }*/


        /*String id = UUID.randomUUID().toString();
        //Create json object
        Jsonb jsonb = JsonbBuilder.create();
        StudentDTO studentDTO = jsonb.fromJson(req.getReader(),StudentDTO.class);
        studentDTO.setId(id);
        System.out.println(studentDTO);*/

        /*Array print jsonb*/
       /* Jsonb jsonb = JsonbBuilder.create();//json bind type object ekk create krla (entry point ek)
        List<StudentDTO> studentDTOList = jsonb.fromJson(req.getReader(), new ArrayList<StudentDTO>(){}.getClass().getGenericSuperclass());
        studentDTOList.forEach(System.out::println);*/

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: get student details
//        var studentDTO = new StudentDTO();


        var dataProcess = new StudentDataProcess();
        var studentId = req.getParameter("id");

        try(var writer = resp.getWriter()) {
            var student = dataProcess.getStudent(studentId, connection);
            System.out.println(student);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(student,writer);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

           /* var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1 , studentId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                studentDTO.setId(resultSet.getString("id"));
                studentDTO.setName(resultSet.getString("name"));
                studentDTO.setCity(resultSet.getString("city"));
                studentDTO.setEmail(resultSet.getString("email"));
                studentDTO.setLevel(resultSet.getString("level"));
            }

            System.out.println(studentDTO);
            resp.setContentType("application/json");
            var jsob = JsonbBuilder.create();
            jsob.toJson(studentDTO,resp.getWriter());*/

    }



    //before implement dao
    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: get student details
        var studentDTO = new StudentDTO();
        var studentId = req.getParameter("id");

        try(var write = resp.getWriter()) {
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1 , studentId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                studentDTO.setId(resultSet.getString("id"));
                studentDTO.setName(resultSet.getString("name"));
                studentDTO.setCity(resultSet.getString("city"));
                studentDTO.setEmail(resultSet.getString("email"));
                studentDTO.setLevel(resultSet.getString("level"));
            }

            System.out.println(studentDTO);
            resp.setContentType("application/json");
            var jsob = JsonbBuilder.create();
            jsob.toJson(studentDTO,resp.getWriter());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    //before implement dao
    /*@Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: update student
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()){
            var ps = this.connection.prepareStatement(UPDATE_STUDENT);
            var studentID = req.getParameter("id");
            *//*var updatedStudent = new StudentDTO();*//*
            Jsonb jsonb = JsonbBuilder.create();
            var updatedStudent = jsonb.fromJson(req.getReader() , StudentDTO.class);
            ps.setString(1,updatedStudent.getName());
            ps.setString(2,updatedStudent.getCity());
            ps.setString(3,updatedStudent.getEmail());
            ps.setString(4,updatedStudent.getLevel());
            ps.setString(5,studentID);
            if (ps.executeUpdate() != 0){

                *//*System.out.println(studentDTO);*//*
                resp.getWriter().write("Student Updated");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                resp.getWriter().write("Update Failed");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.write("update failed");
            }

        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
           *//*throw new RuntimeException(e);*//*
        }

    }*/

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: update student
       /* if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }*/

        try (var writer = resp.getWriter()){

            var studentID = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            var studentDataProcess = new StudentDataProcess();
            var updatedStudent = jsonb.fromJson(req.getReader(), StudentDTO.class);

            if (studentDataProcess.updateStudent(studentID,updatedStudent,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("update failed");
            }

        } catch (JsonException e) {
           /* resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();*/
            throw new RuntimeException(e);
        }

    }

    //before implement dao
   /* @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        //Todo: delete student
        var stuId = req.getParameter("id");
        try{
            var ps = this.connection.prepareStatement(DELETE_STUDENT);
            ps.setString(1,stuId);
            if (ps.executeUpdate() != 0){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                System.out.println("hariyooo");
            }else {
              resp.getWriter().write("deleted failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }*/
   @Override
   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //Todo: delete student
       var stuId = req.getParameter("id");
       try(var writer = resp.getWriter()){
           var studentDataProcess = new StudentDataProcess();
           if(studentDataProcess.deleteStudent(stuId, connection)){
               resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
           }else {
               resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
               writer.write("Delete Failed");
           }
       } catch (JsonException e) {
           resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           throw new RuntimeException(e);

       }
   }
}
