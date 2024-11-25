package co.edu.usco.pw.hotelbackend.services.authentication;

import co.edu.usco.pw.hotelbackend.dto.SignupRequestDTO;
import co.edu.usco.pw.hotelbackend.dto.UserDTO;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;

public interface AuthService {

    UserDTO signupClient(SignupRequestDTO signupRequestDTO);

    Boolean presentByEmail(String email);

    UserDTO signupAdmin(SignupRequestDTO signupRequestDTO);

}
