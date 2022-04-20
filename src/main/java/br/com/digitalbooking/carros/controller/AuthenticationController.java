package br.com.digitalbooking.carros.controller;

import br.com.digitalbooking.carros.config.security.TokenService;
import br.com.digitalbooking.carros.dto.LoginForm;
import br.com.digitalbooking.carros.dto.TokenDTO;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginForm loginForm) {
        if (loginForm.getEmail() == null || loginForm.getPassword() == null) throw new UnreportedEssentialFieldException("Dados de login incompletos");

        UsernamePasswordAuthenticationToken dataLogin = loginForm.asUsernamePasswordAuthenticationToken();
        try {
            Authentication authentication = authenticationManager.authenticate(dataLogin);
            return ResponseEntity.status(HttpStatus.OK).body(tokenService.generateToken(authentication));
        } catch(AuthenticationException ex) {
            return ResponseEntity.badRequest().build();
        }




    }
}
