package co.edu.usco.pw.hotelbackend.services.admin;

import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import co.edu.usco.pw.hotelbackend.dto.UserDTO;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean publishRoom(Long userId, RoomDTO roomDTO) throws IOException;

    List<RoomDTO> getAllRooms(Long userId);

    RoomDTO getRoomById(Long roomId);

    boolean updateRoom(Long roomId, RoomDTO roomDTO) throws IOException;

    boolean deleteRoom(Long roomId);

    boolean deleteReservation(Long reservationId);

    boolean deleteClientById(Long clientId);

    List<ReservationDTO> getAllRoomBookings(Long adminId);

    boolean changeBookingStatus(Long bookingId, String status);

    List<UserDTO> getAllClients();

}
