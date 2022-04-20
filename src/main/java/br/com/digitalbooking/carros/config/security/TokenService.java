package br.com.digitalbooking.carros.config.security;

import br.com.digitalbooking.carros.dto.TokenDTO;
import br.com.digitalbooking.carros.dto.UserDTO;
import br.com.digitalbooking.carros.model.User;
import br.com.digitalbooking.carros.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    //@Autowired
    //private UserRepository userRepository;

    public TokenDTO generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date today = new Date();
        Date dateExpiration = new Date(today.getTime() + Long.parseLong(expiration));

        String token =  Jwts.builder()
                .setIssuer("API do DigitalBooking")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return new TokenDTO(token, "Bearer", new UserDTO(user));
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Long getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
