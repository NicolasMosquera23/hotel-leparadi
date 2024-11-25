package co.edu.usco.pw.hotelbackend.dto;

import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {

    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private UserRole role;

    public UserDTO(UserEntity user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
    }
}
