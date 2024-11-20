package co.edu.usco.pw.hotelbackend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
@Table(name = "reviews")
@Schema(description = "Entity representing a review for a room reservation")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the review", example = "1")
    private Long id;

    @Schema(description = "Date when the review was posted", example = "2024-11-18")
    private Date reviewDate;

    @Schema(description = "The content of the review")
    private String review;

    @Schema(description = "Rating given by the user, typically between 1 and 5")
    private Long rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(description = "The user who posted the review")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(description = "The room that is being reviewed")
    private RoomEntity room;

}
