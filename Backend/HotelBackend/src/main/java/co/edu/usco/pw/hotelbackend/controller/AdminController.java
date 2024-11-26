package co.edu.usco.pw.hotelbackend.controller;

import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.dto.UserDTO;
import co.edu.usco.pw.hotelbackend.services.admin.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Controlador de Administración", description = "Endpoints para gestionar habitaciones y reservas del hotel como administrador")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @PostMapping("/room/{userId}")
    @Operation(summary = "Publicar una nueva habitación", description = "Crea una nueva habitación para un usuario específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Habitación creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de la habitación inválidos o falta el precio"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> publishRoom(
            @PathVariable Long userId,
            @ModelAttribute RoomDTO roomDTO) throws IOException {

        if (roomDTO.getPrice() == null || roomDTO.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falta el precio o es inválido");
        }

        // Guardar la habitación usando el servicio
        boolean success = adminService.publishRoom(userId, roomDTO);

        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/rooms/{userId}")
    @Operation(summary = "Obtener todas las habitaciones por ID de usuario", description = "Recupera todas las habitaciones asociadas a un usuario específico (administrador).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitaciones recuperadas con éxito")
    })
    public ResponseEntity<List<RoomDTO>> getAllRoomsByUserId(
            @Parameter(description = "ID del usuario administrador", example = "1") @PathVariable Long userId) {
        List<RoomDTO> rooms = adminService.getAllRooms(userId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "Obtener habitación por ID", description = "Recupera una habitación específica por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitación recuperada con éxito"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<RoomDTO> getRoomById(
            @Parameter(description = "ID de la habitación a recuperar", example = "1") @PathVariable Long roomId) {
        RoomDTO roomDTO = adminService.getRoomById(roomId);
        return roomDTO != null ? ResponseEntity.ok(roomDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/room/{roomId}")
    @Operation(summary = "Actualizar los detalles de una habitación", description = "Actualiza los detalles de una habitación específica por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitación actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de la habitación inválidos"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<String> updateRoom(
            @Parameter(description = "ID de la habitación a actualizar", example = "1") @PathVariable Long roomId,
            @RequestBody RoomDTO roomDTO) throws IOException {

        if (roomDTO.getRoomType() == null || roomDTO.getRoomType().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falta el tipo de habitación o está vacío");
        }

        if (roomDTO.getPrice() == null || roomDTO.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falta el precio o es inválido");
        }

        boolean success = adminService.updateRoom(roomId, roomDTO);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/room/{roomId}")
    @Operation(summary = "Eliminar una habitación", description = "Elimina una habitación específica por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitación eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<Void> deleteRoomById(
            @Parameter(description = "ID de la habitación a eliminar", example = "1") @PathVariable Long roomId) {
        boolean success = adminService.deleteRoom(roomId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/reservation/{reservationId}")
    @Operation(summary = "Eliminar una reserva", description = "Elimina una reserva específica por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<Void> deleteReservationById(
            @Parameter(description = "ID de la reserva a eliminar", example = "1") @PathVariable Long reservationId) {
        boolean success = adminService.deleteReservation(reservationId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/clients/{clientId}")
    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente específico por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Void> deleteClientById(
            @Parameter(description = "ID del cliente a eliminar", example = "1") @PathVariable Long clientId) {
        boolean success = adminService.deleteClientById(clientId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/bookings/{adminId}")
    @Operation(summary = "Obtener todas las reservas de un administrador", description = "Recupera todas las reservas de habitaciones gestionadas por un administrador específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas recuperadas con éxito")
    })
    public ResponseEntity<List<ReservationDTO>> getAllRoomBookings(
            @Parameter(description = "ID del administrador para recuperar reservas", example = "1") @PathVariable Long adminId) {
        List<ReservationDTO> bookings = adminService.getAllRoomBookings(adminId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/booking/{bookingId}/{status}")
    @Operation(summary = "Cambiar el estado de una reserva", description = "Cambia el estado de una reserva específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado de la reserva actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<Void> changeBookingStatus(
            @Parameter(description = "ID de la reserva a actualizar", example = "1") @PathVariable Long bookingId,
            @Parameter(description = "Nuevo estado para la reserva", example = "CONFIRMED") @PathVariable String status) {
        boolean success = adminService.changeBookingStatus(bookingId, status);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/clients")
    @Operation(summary = "Obtener todos los clientes", description = "Recupera todos los clientes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clientes recuperados con éxito"),
            @ApiResponse(responseCode = "404", description = "No se encontraron clientes")
    })
    public ResponseEntity<List<UserDTO>> getAllClients() {
        List<UserDTO> clients = adminService.getAllClients();
        return clients.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.ok(clients);
    }
}

