package co.edu.usco.pw.hotelbackend.services.admin;

import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    @Operation(summary = "Publish a room", description = "Publishes a room to the platform for a given user")
    boolean publishRoom(Long userId, RoomDTO roomDTO) throws IOException;

    @Operation(summary = "Get all rooms for a user", description = "Fetches all rooms associated with a specific user")
    List<RoomDTO> getAllRooms(Long userId);

    @Operation(summary = "Get a room by its ID", description = "Fetches details of a specific room by its ID")
    RoomDTO getRoomById(Long roomId);

    @Operation(summary = "Update room details", description = "Updates the details of an existing room")
    boolean updateRoom(Long roomId, RoomDTO roomDTO) throws IOException;

    @Operation(summary = "Delete a room", description = "Deletes a specific room by its ID")
    boolean deleteRoom(Long roomId);

    @Operation(summary = "Get all reservations for a room", description = "Fetches all bookings for a specific room by its administrator")
    List<ReservationDTO> getAllRoomBookings(Long adminId);

    @Operation(summary = "Change booking status", description = "Changes the status of a reservation")
    boolean changeBookingStatus(Long bookingId, String status);

}
