package co.edu.usco.pw.hotelbackend.repository;

import co.edu.usco.pw.hotelbackend.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Repository for handling Review entities in the database")
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
