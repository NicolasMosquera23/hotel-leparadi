package co.edu.usco.pw.hotelbackend.services.admin;

import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import co.edu.usco.pw.hotelbackend.entity.RoomEntity;
import co.edu.usco.pw.hotelbackend.entity.ReservationEntity;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.enums.ReservationStatus;
import co.edu.usco.pw.hotelbackend.repository.RoomRepository;
import co.edu.usco.pw.hotelbackend.repository.ReservationRepository;
import co.edu.usco.pw.hotelbackend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    @Operation(summary = "Publish a room", description = "Publishes a room for a specific user")
    public boolean publishRoom(Long userId, RoomDTO roomDTO) throws IOException {

        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            RoomEntity room = new RoomEntity();
            room.setRoomType(roomDTO.getRoomType());
            room.setDescription(roomDTO.getDescription());
            room.setPrice(roomDTO.getPrice());
            room.setUser(optionalUser.get());

            roomRepository.save(room);
            return true;
        }
        return false;
    }

    @Override
    @Operation(summary = "Get all rooms for a user", description = "Fetches all rooms for a specific user")
    public List<RoomDTO> getAllRooms(Long userId) {
        return roomRepository.findAllByUserId(userId).stream()
                .map(RoomEntity::getRoomDto)
                .collect(Collectors.toList());
    }

    @Override
    @Operation(summary = "Get a room by its ID", description = "Fetches a room by its ID")
    public RoomDTO getRoomById(Long roomId) {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isPresent()) {
            return optionalRoom.get().getRoomDto();
        }
        return null;
    }

    @Override
    @Operation(summary = "Update room details", description = "Updates the details of a specific room")
    public boolean updateRoom(Long roomId, RoomDTO roomDTO) throws IOException {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isPresent()) {
            RoomEntity room = optionalRoom.get();

            room.setRoomType(roomDTO.getRoomType());
            room.setDescription(roomDTO.getDescription());
            room.setPrice(roomDTO.getPrice());

            roomRepository.save(room);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Operation(summary = "Delete a room", description = "Deletes a room by its ID")
    public boolean deleteRoom(Long roomId) {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isPresent()) {
            roomRepository.delete(optionalRoom.get());
            return true;
        }
        return false;
    }

    @Override
    @Operation(summary = "Get all reservations for a room", description = "Fetches all reservations made by a specific admin")
    public List<ReservationDTO> getAllRoomBookings(Long adminId) {
        return reservationRepository.findAllByAdminId(adminId)
                .stream().map(ReservationEntity::getReservationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Operation(summary = "Change booking status", description = "Changes the status of a reservation (Approve or Reject)")
    public boolean changeBookingStatus(Long bookingId, String status) {
        Optional<ReservationEntity> optionalReservation = reservationRepository.findById(bookingId);
        if (optionalReservation.isPresent()) {
            ReservationEntity existingReservation = optionalReservation.get();
            if (Objects.equals(status, "Approve")) {
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);
            } else {
                existingReservation.setReservationStatus(ReservationStatus.REJECTED);
            }
            reservationRepository.save(existingReservation);
            return true;
        }
        return false;
    }
}
