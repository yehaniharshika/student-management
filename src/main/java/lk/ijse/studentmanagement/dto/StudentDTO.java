package lk.ijse.studentmanagement.dto;

import lombok.*;

import java.io.Serializable;

//* implement  Serializable for DTO class
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class StudentDTO implements Serializable {
    private String id;
    private String name;
    private String email;
    private String city;
    private String level;
}
