package co.edu.usco.pw.hotelbackend.repository;

import co.edu.usco.pw.hotelbackend.entity.ReservationEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Operation(summary = "Get all reservations by admin ID", description = "Fetches all reservations associated with a specific admin by their ID")
    List<ReservationEntity> findAllByAdminId(Long adminId);

    @Operation(summary = "Get all reservations by user ID", description = "Fetches all reservations associated with a specific user by their ID")
    List<ReservationEntity> findAllByUserId(Long userId);
}
