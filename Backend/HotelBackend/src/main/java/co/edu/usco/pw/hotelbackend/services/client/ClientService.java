package co.edu.usco.pw.hotelbackend.services.client;

import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import co.edu.usco.pw.hotelbackend.dto.RoomDetailsForClientDTO;
import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;

import java.util.List;

public interface ClientService {

    List<RoomDTO> getAllRooms();

    List<RoomDTO> searchRoomByType(String roomType);

    boolean bookService(ReservationDTO reservationDTO);

    RoomDetailsForClientDTO getRoomDetailsByRoomId(Long roomId);

    List<ReservationDTO> getAllBookingsByUserId(Long userId);

}
