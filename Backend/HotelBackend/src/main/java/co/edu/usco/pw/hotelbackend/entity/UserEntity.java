package co.edu.usco.pw.hotelbackend.entity;

import co.edu.usco.pw.hotelbackend.dto.UserDTO;
import co.edu.usco.pw.hotelbackend.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Data

public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserDTO getDto() {
        UserDTO userDto = new UserDTO();
        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setRole(role);

        return userDto;
    }
}
