package co.edu.usco.pw.hotelbackend.services.authentication;

import co.edu.usco.pw.hotelbackend.dto.SignupRequestDTO;
import co.edu.usco.pw.hotelbackend.dto.UserDto;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.enums.UserRole;
import co.edu.usco.pw.hotelbackend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Operation(summary = "Sign up a client", description = "Registers a new client with the provided details")
    public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
        UserEntity user = new UserEntity();

        // Map input DTO to UserEntity
        user.setFirstName(signupRequestDTO.getName());
        user.setLastName(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.CLIENT);

        // Save client and return DTO
        return userRepository.save(user).getDto();
    }

    @Override
    @Operation(summary = "Check if email is present", description = "Checks if the email already exists in the system")
    public Boolean presentByEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    @Override
    @Operation(summary = "Sign up an administrator", description = "Registers a new administrator with the provided details")
    public UserDto signupAdmin(SignupRequestDTO signupRequestDTO) {
        UserEntity user = new UserEntity();

        // Map input DTO to UserEntity
        user.setFirstName(signupRequestDTO.getName());
        user.setLastName(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.ADMIN);

        // Save admin and return DTO
        return userRepository.save(user).getDto();
    }

    @Operation(summary = "Create a user from Google OAuth2 login", description = "Registers or retrieves a user logging in with Google OAuth2")
    public UserEntity createUserFromGoogle(String email, String name) {
        // Check if the user already exists by email
        UserEntity existingUser = userRepository.findFirstByEmail(email);

        if (existingUser != null) {
            return existingUser; // Return existing user if found
        }

        // If user does not exist, create a new one
        UserEntity newUser = new UserEntity();
        newUser.setEmail(email);
        newUser.setFirstName(name); // Assume `name` contains the first name; modify as needed
        newUser.setRole(UserRole.CLIENT); // Default role for Google users

        // Save new user and return it
        return userRepository.save(newUser);
    }
}
