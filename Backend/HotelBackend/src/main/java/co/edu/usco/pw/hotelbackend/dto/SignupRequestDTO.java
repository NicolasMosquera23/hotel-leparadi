package co.edu.usco.pw.hotelbackend.dto;

import co.edu.usco.pw.hotelbackend.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignupRequestDTO {

    @Schema(description = "Unique identifier of the user", example = "1", required = false)
    private Long id;

    @Schema(description = "Email address of the user", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "Password for the user's account", example = "password123", required = true)
    private String password;

    @Schema(description = "First name of the user", example = "John", required = true)
    private String name;

    @Schema(description = "Last name of the user", example = "Doe", required = true)
    private String lastname;

    @Schema(description = "Phone number of the user", example = "+1234567890", required = true)
    private String phone;

    @Schema(description = "Role of the user (e.g., ADMIN, CLIENT)", example = "CLIENT", required = true)
    private UserRole role;
}
