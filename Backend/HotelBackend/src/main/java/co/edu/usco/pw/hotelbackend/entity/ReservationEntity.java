package co.edu.usco.pw.hotelbackend.entity;

import co.edu.usco.pw.hotelbackend.entity.RoomEntity;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.enums.ReservationStatus;
import co.edu.usco.pw.hotelbackend.enums.ReviewStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
@Table(name = "reservations")
@Schema(description = "Entity representing a reservation in the hotel system")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the reservation", example = "1")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the reservation", example = "CONFIRMED")
    private ReservationStatus reservationStatus;


    @Schema(description = "Date when the reservation was made", example = "2024-11-18")
    private Date bookDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(description = "The user who made the reservation")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(description = "The admin who handled the reservation")
    private UserEntity admin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(description = "The room associated with the reservation")
    private RoomEntity room;

    /**
     * Converts the ReservationEntity to a ReservationDTO for API responses.
     * @return ReservationDTO object containing reservation details.
     */
    public ReservationDTO getReservationDto() {
        ReservationDTO dto = new ReservationDTO();

        dto.setId(id);
        dto.setRoomType(room.getRoomType());  // Renamed to `roomType`
        dto.setBookDate(bookDate);
        dto.setReservationStatus(reservationStatus);

        dto.setRoomId(room.getId());  // Renamed to `roomId`
        dto.setAdminId(admin.getId());  // Renamed to `adminId`
        dto.setUserName(user.getFirstName());

        return dto;
    }
}
