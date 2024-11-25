package co.edu.usco.pw.hotelbackend.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;

    private String password;
}
