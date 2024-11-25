package co.edu.usco.pw.hotelbackend.repository;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.enums.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Operation(summary = "Find a user by email", description = "Fetches the first user in the database matching the provided email")
    UserEntity findFirstByEmail(String email);

    List<UserEntity> findByRole(UserRole role);
}
