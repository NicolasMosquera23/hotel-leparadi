package co.edu.usco.pw.hotelbackend.controller;

import co.edu.usco.pw.hotelbackend.dto.AuthenticationRequest;
import co.edu.usco.pw.hotelbackend.dto.SignupRequestDTO;
import co.edu.usco.pw.hotelbackend.dto.UserDto;
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
@RequestMapping("/api/auth") // Asegura una ruta base para la autenticación
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

    @Operation(summary = "Client sign-up", description = "Register a new client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client successfully registered"),
            @ApiResponse(responseCode = "400", description = "Email already exists")
    })
    @PostMapping("/sign-up/client")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO) {
        if (authService.presentByEmail(signupRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Client already exists with this email");
        }
        UserDto createdUser = authService.signupClient(signupRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Admin sign-up", description = "Register a new admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin successfully registered"),
            @ApiResponse(responseCode = "400", description = "Email already exists")
    })
    @PostMapping("/sign-up/admin")
    public ResponseEntity<?> signupAdmin(@RequestBody SignupRequestDTO signupRequestDTO) {
        if (authService.presentByEmail(signupRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Admin already exists with this email");
        }
        UserDto createdUser = authService.signupAdmin(signupRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Authenticate user", description = "Authenticate a user and generate a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password", e);
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
