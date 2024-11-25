package co.edu.usco.pw.hotelbackend.services.authentication;

import co.edu.usco.pw.hotelbackend.dto.SignupRequestDTO;
import co.edu.usco.pw.hotelbackend.dto.UserDTO;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.enums.UserRole;
import co.edu.usco.pw.hotelbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO signupClient(SignupRequestDTO signupRequestDTO) {
        UserEntity user = new UserEntity();

        user.setFirstName(signupRequestDTO.getName());
        user.setLastName(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.CLIENT);

        return userRepository.save(user).getDto();
    }

    @Override
    public Boolean presentByEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    @Override
    public UserDTO signupAdmin(SignupRequestDTO signupRequestDTO) {
        UserEntity user = new UserEntity();

        user.setFirstName(signupRequestDTO.getName());
        user.setLastName(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.ADMIN);

        return userRepository.save(user).getDto();
    }

}
