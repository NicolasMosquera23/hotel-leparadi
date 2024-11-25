package co.edu.usco.pw.hotelbackend.controller;

import org.springframework.security.authentication.AuthenticationManager;
import co.edu.usco.pw.hotelbackend.dto.AuthenticationRequest;
import co.edu.usco.pw.hotelbackend.dto.SignupRequestDTO;
import co.edu.usco.pw.hotelbackend.dto.UserDTO;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.repository.UserRepository;
import co.edu.usco.pw.hotelbackend.services.authentication.AuthService;
import co.edu.usco.pw.hotelbackend.services.jwt.UserDetailsServiceImpl;
import co.edu.usco.pw.hotelbackend.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Operation(summary = "Registro de cliente", description = "Registra un nuevo cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El correo electrónico ya existe")
    })
    @PostMapping("/sign-up/client")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO) {
        if (authService.presentByEmail(signupRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ya existe un cliente con este correo electrónico");
        }
        UserDTO createdUser = authService.signupClient(signupRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Registro de administrador", description = "Registra un nuevo administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El correo electrónico ya existe")
    })
    @PostMapping("/sign-up/admin")
    public ResponseEntity<?> signupAdmin(@RequestBody SignupRequestDTO signupRequestDTO) {
        if (authService.presentByEmail(signupRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ya existe un administrador con este correo electrónico");
        }
        UserDTO createdUser = authService.signupAdmin(signupRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Autenticar usuario", description = "Autentica un usuario y genera un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "401", description = "Nombre de usuario o contraseña inválidos")
    })
    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Nombre de usuario o contraseña inválidos", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        UserEntity user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

        // Imprimir el token para depuración
        System.out.println("Generated JWT: " + jwt);

        response.getWriter().write(new JSONObject()
                .put("userId", user.getId())
                .put("role", user.getRole())
                .toString()
        );

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization," +
                " X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-Header");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }
}

