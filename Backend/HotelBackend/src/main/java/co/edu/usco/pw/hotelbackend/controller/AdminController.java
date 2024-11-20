package co.edu.usco.pw.hotelbackend.controller;

import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.services.admin.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Endpoints for managing hotel rooms and reservations as an administrator")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/room/{userId}")
    @Operation(summary = "Publish a new room", description = "Creates a new room for a specific user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid room data or missing price"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> publishRoom(
            @PathVariable Long userId,
            @ModelAttribute RoomDTO roomDTO) throws IOException {

        if (roomDTO.getPrice() == null || roomDTO.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price is missing or invalid");
        }

        // Guardar la habitación usando el servicio
        boolean success = adminService.publishRoom(userId, roomDTO);

        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/rooms/{userId}")
    @Operation(summary = "Get all rooms by user ID", description = "Retrieves all rooms associated with a specific user (admin).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully")
    })
    public ResponseEntity<List<RoomDTO>> getAllRoomsByUserId(
            @Parameter(description = "User ID of the admin", example = "1") @PathVariable Long userId) {
        List<RoomDTO> rooms = adminService.getAllRooms(userId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "Get room by ID", description = "Retrieves a specific room by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    public ResponseEntity<RoomDTO> getRoomById(
            @Parameter(description = "Room ID to retrieve", example = "1") @PathVariable Long roomId) {
        RoomDTO roomDTO = adminService.getRoomById(roomId);
        return roomDTO != null ? ResponseEntity.ok(roomDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/room/{roomId}")
    @Operation(summary = "Update room details", description = "Updates the details of a specific room by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid room data"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    public ResponseEntity<String> updateRoom(
            @Parameter(description = "Room ID to update", example = "1") @PathVariable Long roomId,
            @RequestBody RoomDTO roomDTO) throws IOException {

        if (roomDTO.getRoomType() == null || roomDTO.getRoomType().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room type is missing or empty");
        }

        if (roomDTO.getPrice() == null || roomDTO.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price is missing or invalid");
        }

        boolean success = adminService.updateRoom(roomId, roomDTO);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/room/{roomId}")
    @Operation(summary = "Delete a room", description = "Deletes a specific room by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    public ResponseEntity<Void> deleteRoomById(
            @Parameter(description = "Room ID to delete", example = "1") @PathVariable Long roomId) {
        boolean success = adminService.deleteRoom(roomId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/bookings/{adminId}")
    @Operation(summary = "Get all bookings for an admin", description = "Retrieves all bookings for rooms managed by a specific admin.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    })
    public ResponseEntity<List<ReservationDTO>> getAllRoomBookings(
            @Parameter(description = "Admin ID to retrieve bookings for", example = "1") @PathVariable Long adminId) {
        List<ReservationDTO> bookings = adminService.getAllRoomBookings(adminId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/booking/{bookingId}/{status}")
    @Operation(summary = "Change booking status", description = "Changes the status of a specific booking.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<Void> changeBookingStatus(
            @Parameter(description = "Booking ID to update", example = "1") @PathVariable Long bookingId,
            @Parameter(description = "New status for the booking", example = "CONFIRMED") @PathVariable String status) {
        boolean success = adminService.changeBookingStatus(bookingId, status);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
