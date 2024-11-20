package co.edu.usco.pw.hotelbackend.dto;

import co.edu.usco.pw.hotelbackend.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object representing a user in the hotel system")
public class UserDto {

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

    @Schema(description = "Role of the user (e.g., ADMIN, CLIENT)", example = "ADMIN")
    private UserRole role;
}
