package co.edu.usco.pw.hotelbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @Schema(description = "Username of the user", example = "john_doe", required = true)
    private String username;

    @Schema(description = "Password of the user", example = "password123", required = true)
    private String password;
}
