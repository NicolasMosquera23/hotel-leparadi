package co.edu.usco.pw.hotelbackend.entity;

import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
@Table(name = "reservations")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private Date bookDate;

    private Date bookDateEnd;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity admin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RoomEntity room;

    public ReservationDTO getReservationDto() {
        ReservationDTO dto = new ReservationDTO();

        dto.setId(id);
        dto.setRoomType(room.getRoomType());
        dto.setBookDate(bookDate);
        dto.setBookDateEnd(bookDateEnd);
        dto.setReservationStatus(reservationStatus);

        dto.setRoomId(room.getId());
        dto.setAdminId(admin.getId());
        dto.setUserName(user.getFirstName());
        dto.setUserLastName(user.getLastName());

        return dto;
    }
}
