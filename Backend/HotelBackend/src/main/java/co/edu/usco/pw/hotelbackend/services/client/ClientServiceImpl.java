package co.edu.usco.pw.hotelbackend.services.client;

import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import co.edu.usco.pw.hotelbackend.dto.RoomDetailsForClientDTO;
import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.entity.RoomEntity;
import co.edu.usco.pw.hotelbackend.entity.ReservationEntity;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.enums.ReservationStatus;
import co.edu.usco.pw.hotelbackend.repository.RoomRepository;
import co.edu.usco.pw.hotelbackend.repository.ReservationRepository;
import co.edu.usco.pw.hotelbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(RoomEntity::getRoomDto).collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> searchRoomByType(String roomType) {
        return roomRepository.findAllByRoomTypeContaining(roomType).stream().map(RoomEntity::getRoomDto).collect(Collectors.toList());
    }

    @Override
    public boolean bookService(ReservationDTO reservationDTO) {

        Optional<RoomEntity> optionalRoom = roomRepository.findById(reservationDTO.getRoomId());
        Optional<UserEntity> optionalUser = userRepository.findById(reservationDTO.getUserId());

        if (optionalRoom.isPresent() && optionalUser.isPresent()) {
            ReservationEntity reservation = new ReservationEntity();

            reservation.setBookDate(reservationDTO.getBookDate());
            reservation.setBookDateEnd(reservationDTO.getBookDateEnd());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservation.setUser(optionalUser.get());

            reservation.setRoom(optionalRoom.get());
            reservation.setAdmin(optionalRoom.get().getUser());

            reservationRepository.save(reservation);

            return true;
        }
        return false;
    }


    @Override
    public RoomDetailsForClientDTO getRoomDetailsByRoomId(Long roomId) {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomId);
        RoomDetailsForClientDTO roomDetailsForClientDTO = new RoomDetailsForClientDTO();
        if (optionalRoom.isPresent()) {
            roomDetailsForClientDTO.setRoomDTO(optionalRoom.get().getRoomDto());
        }
        return roomDetailsForClientDTO;
    }

    @Override
    public List<ReservationDTO> getAllBookingsByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        return reservationRepository.findAllByUserId(userId)
                .stream().map(ReservationEntity::getReservationDto).collect(Collectors.toList());
    }
}



