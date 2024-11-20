package co.edu.usco.pw.hotelbackend.services.client;

import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import co.edu.usco.pw.hotelbackend.dto.RoomDetailsForClientDTO;
import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

public interface ClientService {

    /**
     * Retrieves all available rooms.
     *
     * @return a list of RoomDTO objects
     */
    @Operation(summary = "Get all rooms", description = "Fetches a list of all available rooms")
    List<RoomDTO> getAllRooms();

    /**
     * Searches for rooms by room type.
     *
     * @param roomType the type of room to search for
     * @return a list of RoomDTO objects matching the room type
     */
    @Operation(summary = "Search rooms by type", description = "Searches for rooms by the specified type")
    List<RoomDTO> searchRoomByType(String roomType);

    /**
     * Books a room based on the provided reservation details.
     *
     * @param reservationDTO the reservation details to book a room
     * @return true if the booking is successful, false otherwise
     */
    @Operation(summary = "Book a room", description = "Books a room based on the provided reservation details")
    boolean bookService(ReservationDTO reservationDTO);

    /**
     * Retrieves the details of a room by its ID.
     *
     * @param roomId the ID of the room
     * @return a RoomDetailsForClientDTO object with the room's details
     */
    @Operation(summary = "Get room details by ID", description = "Fetches the details of a room based on its ID")
    RoomDetailsForClientDTO getRoomDetailsByRoomId(Long roomId);

    /**
     * Retrieves all bookings made by a user.
     *
     * @param userId the ID of the user
     * @return a list of ReservationDTO objects for the specified user
     */
    @Operation(summary = "Get all bookings by user", description = "Fetches a list of all bookings made by the user")
    List<ReservationDTO> getAllBookingsByUserId(Long userId);

    // Uncomment and implement this method when needed
    // Boolean giveReview(ReviewDTO reviewDTO);
}
