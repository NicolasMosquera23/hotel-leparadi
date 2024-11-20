package co.edu.usco.pw.hotelbackend.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Defines the different user roles in the system.")
public enum UserRole {

    @Schema(description = "Role for clients who use the service", example = "CLIENT")
    CLIENT,

    @Schema(description = "Role for administrators who manage the system", example = "ADMIN")
    ADMIN;

}
