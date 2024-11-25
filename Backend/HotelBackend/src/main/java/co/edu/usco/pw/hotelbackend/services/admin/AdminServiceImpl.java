package co.edu.usco.pw.hotelbackend.services.admin;

import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import co.edu.usco.pw.hotelbackend.dto.UserDTO;
import co.edu.usco.pw.hotelbackend.entity.RoomEntity;
import co.edu.usco.pw.hotelbackend.entity.ReservationEntity;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.enums.ReservationStatus;
import co.edu.usco.pw.hotelbackend.enums.UserRole;
import co.edu.usco.pw.hotelbackend.repository.RoomRepository;
import co.edu.usco.pw.hotelbackend.repository.ReservationRepository;
import co.edu.usco.pw.hotelbackend.repository.UserRepository;
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
    public boolean deleteClientById(Long clientId) {
        if (userRepository.existsById(clientId)) {
            userRepository.deleteById(clientId);
            return true;
        }
        return false;
    }

    @Override
    public List<RoomDTO> getAllRooms(Long userId) {
        return roomRepository.findAllByUserId(userId).stream()
                .map(RoomEntity::getRoomDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(Long roomId) {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isPresent()) {
            return optionalRoom.get().getRoomDto();
        }
        return null;
    }

    @Override
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
    public boolean deleteRoom(Long roomId) {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isPresent()) {
            roomRepository.delete(optionalRoom.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReservation(Long reservationId) {
        if (reservationRepository.existsById(reservationId)) {
            reservationRepository.deleteById(reservationId);
            return true;
        }
        return false;
    }

    @Override
    public List<ReservationDTO> getAllRoomBookings(Long adminId) {
        return reservationRepository.findAllByAdminId(adminId)
                .stream().map(ReservationEntity::getReservationDto)
                .collect(Collectors.toList());
    }

    @Override
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

    @Override
    public List<UserDTO> getAllClients() {
        List<UserEntity> users = userRepository.findByRole(UserRole.CLIENT);
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
        }
}
