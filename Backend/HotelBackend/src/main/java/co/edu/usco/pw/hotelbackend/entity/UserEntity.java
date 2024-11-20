package co.edu.usco.pw.hotelbackend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import co.edu.usco.pw.hotelbackend.dto.UserDto;
import co.edu.usco.pw.hotelbackend.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Schema(description = "Entity representing a user in the hotel system")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user", example = "1")
    private Long id;

    @Schema(description = "Email of the user")
    private String email;

    @Schema(description = "Password of the user")
    private String password;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Phone number of the user")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Role of the user (e.g., ADMIN, CLIENT)", example = "ADMIN")
    private UserRole role;

    public UserDto getDto() {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setRole(role);

        return userDto;
    }
}
