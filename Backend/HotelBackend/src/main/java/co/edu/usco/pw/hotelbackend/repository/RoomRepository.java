package co.edu.usco.pw.hotelbackend.repository;

import co.edu.usco.pw.hotelbackend.entity.RoomEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    @Operation(summary = "Get all rooms by user ID", description = "Fetches all rooms associated with a specific user by their ID")
    List<RoomEntity> findAllByUserId(Long userId);

    @Operation(summary = "Search rooms by type", description = "Searches for rooms that contain a specific type in their name")
    List<RoomEntity> findAllByRoomTypeContaining(String roomType);
}
