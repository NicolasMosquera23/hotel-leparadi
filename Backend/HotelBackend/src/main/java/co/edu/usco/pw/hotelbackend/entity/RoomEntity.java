package co.edu.usco.pw.hotelbackend.entity;

import co.edu.usco.pw.hotelbackend.dto.RoomDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "rooms")
@Data
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private long id;

    private String roomType;

    private String description;

    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
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
