package co.edu.usco.pw.hotelbackend.dto;

import co.edu.usco.pw.hotelbackend.entity.ReservationEntity;
import co.edu.usco.pw.hotelbackend.enums.ReservationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class ReservationDTO {

    private Long id;

    private Date bookDate;

    private Date bookDateEnd;

    private String roomType;

    private ReservationStatus reservationStatus;

    private Long userId;

    private String userName;

    private String userLastName;

    private Long adminId;

    private Long roomId;

    public ReservationDTO(ReservationEntity reservation) {
        this.id = reservation.getId();
        this.bookDate = reservation.getBookDate();
        this.bookDateEnd = reservation.getBookDateEnd();
        this.userName = reservation.getUser().getFirstName();
        this.userLastName = reservation.getUser().getLastName();
        this.reservationStatus = reservation.getReservationStatus(); }

}
