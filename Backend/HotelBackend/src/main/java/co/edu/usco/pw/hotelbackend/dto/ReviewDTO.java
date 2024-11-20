package co.edu.usco.pw.hotelbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {

    @Schema(description = "Unique identifier for the review", example = "1", required = true)
    private Long id;

    @Schema(description = "The date when the review was posted", example = "2024-11-18T10:00:00Z", required = true)
    private Date reviewDate;

    @Schema(description = "The content of the review", example = "Excellent room, would definitely stay again.", required = true)
    private String review;

    @Schema(description = "Rating given by the user", example = "5", required = true)
    private Long rating;

    @Schema(description = "The unique identifier of the user who posted the review", example = "101", required = true)
    private Long userId;

    @Schema(description = "The unique identifier of the room being reviewed", example = "3", required = true)
    private Long roomId;

    @Schema(description = "The name of the client who posted the review", example = "John Doe", required = true)
    private String clientName;

    @Schema(description = "Type of room being reviewed", example = "Deluxe Room", required = true)
    private String roomType;

    @Schema(description = "The unique identifier of the reservation associated with the review", example = "1", required = true)
    private Long bookId;
}
