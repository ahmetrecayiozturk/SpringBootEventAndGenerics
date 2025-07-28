package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.checkerframework.checker.units.qual.A;
import org.example.aop.jwt.CheckTokenExpirationTime;
import org.example.aop.log.LogExecutionTime;
import org.example.aop.role.CheckRole;
import org.example.dto.RegisterRequest;
import org.example.event.event_generic.AppEvent;
import org.example.event.event_service.DomainEventPublisher;
import org.example.event.events.CreatedUserEvent;
import org.example.event.events.LogoutUserEvent;
import org.example.generics.ApiResponse;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.security.JwtUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.example.dto.AuthRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authManager;

    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    private PasswordEncoder passwordEncoder;

    private DomainEventPublisher domainEventPublisher;

    public AuthController(AuthenticationManager authManager,
                          UserRepository userRepository,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder,
                          DomainEventPublisher domainEventPublisher) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.domainEventPublisher = domainEventPublisher;
    }

    @LogExecutionTime
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        else{
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            //şifreyi encode ederek veriyoruz
            newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            //varsayılan rol olarak User dedim ama bunu sonradan değiştirebiliriz eğer requestte verseydik falan orda haleldebilirdik mesela
            //alıcı ve satıcı kayıt olurken bunu kayıt sırasında belirtince burada biz onu rol olarak verebiliriz, bu default hali
            newUser.setRole(registerRequest.getRole());
            userRepository.save(newUser);
            CreatedUserEvent userRegisteredEvent = new CreatedUserEvent(newUser);
            domainEventPublisher.publish(userRegisteredEvent);
            return ResponseEntity.ok("User registered successfully");
        }
    }

    @LogExecutionTime
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest){
            //burada authenticationManager'ı kullanarak kullanıcıyı authenticate ediyoruz, bunu yaparken UsernamePasswordAuthenticationToken kullanıyoruz
            //bu token, kullanıcı adı ve şifre ile oluşturuluyor ve authenticationManager tarafından doğrulanıyor.
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()));
            User user = userRepository.findByUsername(authRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if(auth.isAuthenticated()){
                //eğer kullanıcı doğru ise yani authentication başarılıysa jwt token oluşturuyoruz
                String token = jwtUtil.generateToken(authRequest.getUsername(),user.getRole());
                return ResponseEntity.ok(Map.of("token", token).toString());
            } else {
                //eğer kullanıcı doğrulanmadıysa, hata mesajı döndürüyoruz
                return ResponseEntity.status(401).body("Invalid username or password");
            }
    }

    @PostMapping("/logout")
    public ApiResponse<User> userLogout (@RequestBody AuthRequest authRequest)
    {
        String name = authRequest.getUsername();
        ApiResponse apiResponse = new ApiResponse();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        LogoutUserEvent logoutUserEvent = new LogoutUserEvent(user);
        apiResponse.setMessage(name + "Logoutu successful geçti");
        domainEventPublisher.publish(logoutUserEvent);
        return apiResponse;

    }
}
