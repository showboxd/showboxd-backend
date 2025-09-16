package com.app.showboxd.auth.service;

import com.app.showboxd.user.service.ShowboxdUserDetailsService;
import com.app.showboxd.user.dto.UserDto;
import com.app.showboxd.user.entity.User;
import com.app.showboxd.user.repository.UserRepository;
import com.app.showboxd.auth.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {

    @Value("${spring.security.jwt.expiration.days}")
    private int expirationDays;

    @Value("${spring.security.jwt.secret.key}")
    private String jwtSecretKey;


    private final AuthenticationManager authManager;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final ShowboxdUserDetailsService showboxdUserDetailsService;

    public LoginService(AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, ShowboxdUserDetailsService userDetailsService) {
        this.authManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.showboxdUserDetailsService = userDetailsService;
    }

    public ResponseEntity<UserDto> login(UserDto userDto, HttpServletResponse response) {
        try{
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getEmail(), userDto.getPassword()
                    )
            );
            if(authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();
                String jwtToken = JWTUtil.generateJwtToken(user, expirationDays, jwtSecretKey);
                Cookie cookie = getAuthCookei(jwtToken);
                response.addCookie(cookie);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            log.error("Failed to login error {}, username = {}", e.getMessage(), userDto.getUsername());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<UserDto> register(UserDto userDto, HttpServletResponse response) {
        try {
            UserDetails userDetails = showboxdUserDetailsService.loadUserByUsername(userDto.getEmail());
            if(userDetails != null) {
                log.error("User already exists user = {}", userDetails.getUsername());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            User user = new User();
            BeanUtils.copyProperties(userDto, user, "password");
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            String jwtToken = JWTUtil.generateJwtToken(user, expirationDays, jwtSecretKey);
            Cookie cookie = getAuthCookei(jwtToken);
            response.addCookie(cookie);
            userDto.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (Exception e) {
            log.error("Error creating new User , userName = {}, email = {}", userDto.getUsername(), userDto.getEmail());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private Cookie getAuthCookei(String jwtToken) {
        Cookie cookie = new Cookie("authToken", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(864000);
        cookie.setPath("/");
        return cookie;
    }
}
