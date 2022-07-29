package com.example.demo.controllers;

import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.services.impl.UserDetailsImpl;
import com.example.demo.services.impl.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
	private final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired private UserServiceImpl userservice;
    
    //Login
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        
        logger.info(String.format("User logged in with token = %s", jwt));

        return ResponseEntity.ok(new JwtResponse(jwt,
                                                userDetails.getId(),
                                                userDetails.getUsername(),
                                                userDetails.getEmail(),
                                                roles));

    }

    //register
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if(Boolean.TRUE.equals(userRepository.existsByUsername(signupRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken"));
        }
        if(Boolean.TRUE.equals(userRepository.existsByEmail(signupRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use"));
        }
        if(Boolean.TRUE.equals(userRepository.existsByMobno(signupRequest.getMobno()))) {
        	return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Mobile Number is already in use"));
        }
        User user = new User(signupRequest.getUsername(),
                            signupRequest.getEmail(),
                            encoder.encode(signupRequest.getPassword()),signupRequest.getMobno());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        String roleNotFound = "Error: Role is not found.";
        if(strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(roleNotFound));
            roles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                
                if(role.equals("admin")) {
                	Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() ->  new RuntimeException(roleNotFound));
                    roles.add(adminRole);
                }
                else {
                	Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(roleNotFound));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        logger.info(String.format("Registered User =  %s", user.getUsername()));
        return ResponseEntity.ok("User registered successfully!");
    }
    
    @PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email){
		String response = userservice.forgotPassword(email);
		String token = null;
		
		if(!response.startsWith("Invalid")) {
			token = response;
		}
		logger.info(String.format("Token for user = %s", token));
		
		return new ResponseEntity<>(token,HttpStatus.OK);
	}
	
	@PutMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam String token,@RequestParam String password){
		String response = userservice.resetPassword(token, password);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
