package co.edu.usco.pw.hotelbackend.dto;

import co.edu.usco.pw.hotelbackend.enums.UserRole;
import lombok.Data;

@Data
public class SignupRequestDTO {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String lastname;

    private String phone;

    private UserRole role;
}
