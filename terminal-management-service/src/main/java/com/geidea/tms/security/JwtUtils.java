package com.geidea.tms.security;

import com.geidea.tms.model.dao.TerminalRepository;
import com.geidea.tms.model.dso.Terminal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;


@Component
public class JwtUtils {
    private final String jwtSecret;
    private final Environment env;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TerminalRepository terminalRepository;

    @Autowired

    public JwtUtils(Environment env, TerminalRepository terminalRepository, @Value("${server.jwt.token.secret}") String jwtSecret) {
        this.env = env;
        this.terminalRepository = terminalRepository;
        this.jwtSecret = jwtSecret;
    }

    public String generateJwtToken(String username) {
        long jwtExpirationMs = Long.parseLong(env.getProperty("server.jwt.token.expiry.time", "3600000"));
        Optional<Terminal> terminal = terminalRepository.findByUserName(username);
        return Jwts.builder()
                .setSubject(terminal.get().getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    Boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        }
        return false;
    }
}
