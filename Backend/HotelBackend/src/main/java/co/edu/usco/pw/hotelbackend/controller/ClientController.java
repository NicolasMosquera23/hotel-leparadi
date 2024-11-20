package co.edu.usco.pw.hotelbackend.controller;
import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.services.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Get all rooms", description = "Retrieve all available rooms for the client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved rooms"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/rooms")
    public ResponseEntity<?> getAllRooms() {
        try {
            return ResponseEntity.ok(clientService.getAllRooms());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving rooms");
        }
    }

    @Operation(summary = "Search room by type", description = "Search for rooms by room type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rooms found based on type"),
            @ApiResponse(responseCode = "404", description = "No rooms found with the specified type")
    })
    @GetMapping("/search/{roomType}")
    public ResponseEntity<?> searchRoomByType(@PathVariable String roomType) {
        try {
            return ResponseEntity.ok(clientService.searchRoomByType(roomType));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rooms found with this type");
        }
    }

    @Operation(summary = "Book a room service", description = "Book a service for a room.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully booked service"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Room or service not found")
    })
    @PostMapping("/book-service")
    public ResponseEntity<?> bookService(@RequestBody ReservationDTO reservationDTO) {

        boolean success = clientService.bookService(reservationDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room or service not found");
        }
    }

    @Operation(summary = "Get room details", description = "Get detailed information about a room by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getRoomDetailsByRoomId(@PathVariable Long roomId) {
        try {
            return ResponseEntity.ok(clientService.getRoomDetailsByRoomId(roomId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }
    }

    @Operation(summary = "Get all bookings by user ID", description = "Get all bookings made by a client using their user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No bookings found for this user"),
            @ApiResponse(responseCode = "400", description = "User ID is missing")
    })
    @GetMapping("/my-bookings/{userId}")
    public ResponseEntity<?> getAllBookingsByUserId(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID is missing");
        }

        try {
            return ResponseEntity.ok(clientService.getAllBookingsByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found for this user");
        }
    }
}


