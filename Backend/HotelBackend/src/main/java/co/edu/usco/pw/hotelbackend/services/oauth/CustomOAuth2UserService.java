package co.edu.usco.pw.hotelbackend.services.oauth;

import co.edu.usco.pw.hotelbackend.enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Asignar rol de CLIENT a cualquier usuario que inicie sesión a través de Google
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(UserRole.CLIENT.name())),
                oAuth2User.getAttributes(),
                "email" // El atributo que identifica al usuario
        );
    }
}
