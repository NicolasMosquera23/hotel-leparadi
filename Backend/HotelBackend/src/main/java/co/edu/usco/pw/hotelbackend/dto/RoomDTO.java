package co.edu.usco.pw.hotelbackend.dto;

import lombok.Data;

@Data
public class RoomDTO {

    private long id;

    private String roomType;

    private String description;

    private Double price;

    private Long userId;

    private String AdminName;
}
