package co.edu.usco.pw.hotelbackend.services.authentication;

import co.edu.usco.pw.hotelbackend.dto.SignupRequestDTO;
import co.edu.usco.pw.hotelbackend.dto.UserDto;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;

public interface AuthService {

    @Operation(summary = "Sign up a client", description = "Registers a new client user with the provided details")
    UserDto signupClient(SignupRequestDTO signupRequestDTO);

    @Operation(summary = "Check if email is present", description = "Checks if an email is already registered in the system")
    Boolean presentByEmail(String email);

    @Operation(summary = "Sign up an administrator", description = "Registers a new administrator user with the provided details")
    UserDto signupAdmin(SignupRequestDTO signupRequestDTO);

    UserEntity createUserFromGoogle(String email, String name);
}
