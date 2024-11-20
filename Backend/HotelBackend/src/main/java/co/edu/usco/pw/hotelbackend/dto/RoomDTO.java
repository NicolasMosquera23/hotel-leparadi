package co.edu.usco.pw.hotelbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoomDTO {

    @Schema(description = "Unique identifier of the room", example = "1", required = true)
    private long id;

    @Schema(description = "Type of the room", example = "Single", required = true)
    private String roomType;

    @Schema(description = "Description of the room", example = "A cozy single room with a king-size bed", required = true)
    private String description;

    @Schema(description = "Price per night for the room", example = "100.00", required = true)
    private Double price;


    @Schema(description = "ID of the user who owns the room", example = "101", required = true)
    private Long userId;

    @Schema(description = "Name of the administrator who manages the room", example = "John Doe", required = true)
    private String AdminName;
}
