package com.stock.stock.Controller;

import com.stock.stock.user.User;
import com.stock.stock.user.UserRepository;
import com.stock.stock.user.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/conta")

public class ContaController {

    @Value("${application.APP_ID}")
    private String APP_ID;

    @Value("${application.YOUR_URL}")
    private String YOUR_URL;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/autoriza")
    public ResponseEntity<String> autoriza (
            HttpServletRequest request
    ) {
        final String userEmail;
        final String jwt;
        final String authHeader = request.getHeader("Authorization");
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        Optional<User> user = userRepository.findByEmail(userEmail);
        return ResponseEntity.ok("https://auth.mercadolivre.com.br/authorization?response_type=code&client_id="+APP_ID+"&redirect_uri="+YOUR_URL+ "&state="+ user.get().getId());
    }









}
