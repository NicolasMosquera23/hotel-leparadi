package co.edu.usco.pw.hotelbackend.dto;

import co.edu.usco.pw.hotelbackend.enums.ReservationStatus;
import co.edu.usco.pw.hotelbackend.enums.ReviewStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDTO {

    @Schema(description = "Unique identifier for the reservation", example = "1", required = true)
    private Long id;

    @Schema(description = "The date when the reservation was made", example = "2024-11-18T10:00:00Z", required = true)
    private Date bookDate;

    @Schema(description = "Type of room reserved", example = "Deluxe Room", required = true)
    private String roomType;

    @Schema(description = "The status of the reservation", example = "PENDING", required = true)
    private ReservationStatus reservationStatus;

    @Schema(description = "The unique identifier of the user who made the reservation", example = "101", required = true)
    private Long userId;

    @Schema(description = "The name of the user who made the reservation", example = "John Doe", required = true)
    private String userName;

    @Schema(description = "The unique identifier of the administrator who approved the reservation", example = "5", required = true)
    private Long adminId;

    @Schema(description = "The unique identifier of the room reserved", example = "3", required = true)
    private Long roomId;
}
