package co.edu.usco.pw.hotelbackend.entity;

import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "rooms")
@Data
@Schema(description = "Entity representing a room in the hotel")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the room", example = "1")
    @Column(name = "room_id")
    private long id;

    @Schema(description = "Type of the room (e.g., Single, Double, Suite)")
    private String roomType;

    @Schema(description = "Description of the room")
    private String description;

    @Schema(description = "Price per night for the room")
    private Double price;

    // @Lob
    // @Column(columnDefinition = "bytea")
    // private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(description = "The user (administrator) who manages the room")
    private UserEntity user;

    public RoomDTO getRoomDto() {
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(id);
        roomDTO.setRoomType(roomType);
        roomDTO.setDescription(description);
        roomDTO.setPrice(price);
        roomDTO.setAdminName(user.getFirstName());

        return roomDTO;
    }
}
