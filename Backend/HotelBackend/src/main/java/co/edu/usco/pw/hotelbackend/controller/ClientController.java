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

    @Operation(summary = "Obtener todas las habitaciones", description = "Recupera todas las habitaciones disponibles para el cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitaciones recuperadas exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/rooms")
    public ResponseEntity<?> getAllRooms() {
        try {
            return ResponseEntity.ok(clientService.getAllRooms());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar las habitaciones");
        }
    }

    @Operation(summary = "Buscar habitación por tipo", description = "Busca habitaciones por tipo de habitación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitaciones encontradas según el tipo"),
            @ApiResponse(responseCode = "404", description = "No se encontraron habitaciones con el tipo especificado")
    })
    @GetMapping("/search/{roomType}")
    public ResponseEntity<?> searchRoomByType(@PathVariable String roomType) {
        try {
            return ResponseEntity.ok(clientService.searchRoomByType(roomType));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron habitaciones con este tipo");
        }
    }

    @Operation(summary = "Reservar un servicio de habitación", description = "Reserva un servicio para una habitación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio reservado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "404", description = "Habitación o servicio no encontrados")
    })
    @PostMapping("/book-service")
    public ResponseEntity<?> bookService(@RequestBody ReservationDTO reservationDTO) {

        boolean success = clientService.bookService(reservationDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habitación o servicio no encontrados");
        }
    }

    @Operation(summary = "Actualizar una reserva", description = "Actualiza los detalles de una reserva existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de reserva inválidos"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada") })
    @PutMapping("/update-booking/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable Long bookingId, @RequestBody ReservationDTO reservationDTO) {
        boolean success = clientService.updateBooking(bookingId, reservationDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada o datos inválidos");
        }
    }

    @Operation(summary = "Obtener detalles de habitación", description = "Obtiene información detallada sobre una habitación por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles de la habitación recuperados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getRoomDetailsByRoomId(@PathVariable Long roomId) {
        try {
            return ResponseEntity.ok(clientService.getRoomDetailsByRoomId(roomId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habitación no encontrada");
        }
    }

    @Operation(summary = "Obtener todas las reservas por ID de usuario", description = "Obtiene todas las reservas realizadas por un cliente usando su ID de usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas recuperadas exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron reservas para este usuario"),
            @ApiResponse(responseCode = "400", description = "Falta el ID de usuario")
    })
    @GetMapping("/my-bookings/{userId}")
    public ResponseEntity<?> getAllBookingsByUserId(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falta el ID de usuario");
        }

        try {
            return ResponseEntity.ok(clientService.getAllBookingsByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron reservas para este usuario");
        }
    }
}


