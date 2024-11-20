package co.edu.usco.pw.hotelbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoomDetailsForClientDTO {

    @Schema(description = "Room details for the client, including room information", required = true)
    private RoomDTO roomDTO;
}
