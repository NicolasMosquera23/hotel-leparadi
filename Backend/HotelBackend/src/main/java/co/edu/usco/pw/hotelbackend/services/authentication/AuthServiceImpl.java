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

        // Se corrigieron los nombres de las propiedades para que coincidan con la entidad en inglés
        user.setFirstName(signupRequestDTO.getName());
        user.setLastName(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.CLIENT);

        // Se guarda el nuevo cliente y se retorna el DTO correspondiente
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

        // Se corrigieron los nombres de las propiedades para que coincidan con la entidad en inglés
        user.setFirstName(signupRequestDTO.getName());
        user.setLastName(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.ADMIN);

        // Se guarda el nuevo administrador y se retorna el DTO correspondiente
        return userRepository.save(user).getDto();
    }
}
