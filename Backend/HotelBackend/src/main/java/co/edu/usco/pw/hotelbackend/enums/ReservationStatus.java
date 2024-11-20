package co.edu.usco.pw.hotelbackend.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Defines the various reservation statuses in the system.")
public enum ReservationStatus {

    @Schema(description = "The reservation is pending approval", example = "PENDING")
    PENDING,

    @Schema(description = "The reservation has been approved", example = "APPROVED")
    APPROVED,

    @Schema(description = "The reservation has been rejected", example = "REJECTED")
    REJECTED;

}
