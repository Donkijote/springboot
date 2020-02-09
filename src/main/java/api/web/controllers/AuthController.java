package api.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.web.mappings.AuthRequest;
import api.web.mappings.AuthResponse;
import api.web.mappings.Signup;
import api.web.models.Users;
import api.web.server.IUserRepository;
import api.web.services.CustomUserDetailsService;
import api.web.utils.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private Jwt JWT;
    @Autowired
    private IUserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody AuthRequest data) throws Exception {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
        } catch (BadCredentialsException e) {

            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        final UserDetails user = userDetailsService.loadUserByUsername(data.getUsername());

        final String jwt = JWT.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> Logup(@RequestBody Signup data) throws Exception {

        Users user = userRepo.findByEmail(data.getEmail());

        if (user != null)
            return ResponseEntity.status(401).body("Email already exists");

        user = userRepo.findByUserName(data.getUserName());

        if (user != null)
            return ResponseEntity.status(401).body("Username already exists");

        try {
            Users newUser = new Users(data.getName(), data.getLastName(), data.getUserName(), data.getEmail(),
                    bcrypt.encode(data.getPassword()));
            userRepo.save(newUser);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create user");
        }

        return ResponseEntity.ok(data);
    }

}