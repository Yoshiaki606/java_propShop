package com.example.auth.controller;

import com.example.auth.entity.User;
import com.example.auth.security.JwtTokenProvider;
import com.example.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    @SuppressWarnings("rawtypes")
    private final FindByIndexNameSessionRepository sessionRepository;

    @SuppressWarnings("rawtypes")
    public AuthController(AuthenticationManager authenticationManager, 
                          UserService userService, 
                          JwtTokenProvider tokenProvider,
                          FindByIndexNameSessionRepository sessionRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email has already been registered!"));
        }
        User createdUser = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginRequest, HttpServletRequest request) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // 1. Authenticate credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Control concurrent logins: Invalidate previous sessions in Redis for this user
        @SuppressWarnings("unchecked")
        Map<String, ? extends Session> userSessions = sessionRepository.findByPrincipalName(email);
        int invalidatedSessionsCount = 0;
        if (userSessions != null && !userSessions.isEmpty()) {
            for (String sessionId : userSessions.keySet()) {
                sessionRepository.deleteById(sessionId);
                invalidatedSessionsCount++;
            }
        }

        // 3. Create new session in Redis for tracking
        HttpSession session = request.getSession(true);
        session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, email);

        // 4. Generate JWT
        String jwt = tokenProvider.generateToken(authentication);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("email", email);
        response.put("invalidatedPreviousSessions", invalidatedSessionsCount);
        response.put("message", "Login successful. Previous sessions invalidated: " + invalidatedSessionsCount);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sessions/active-count")
    public ResponseEntity<?> getActiveSessionsCount(@RequestParam("email") String email) {
        @SuppressWarnings("unchecked")
        Map<String, ? extends Session> userSessions = sessionRepository.findByPrincipalName(email);
        int activeCount = userSessions != null ? userSessions.size() : 0;
        return ResponseEntity.ok(Map.of("email", email, "activeSessionsCount", activeCount));
    }
}
