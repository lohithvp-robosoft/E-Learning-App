package com.robosoft.elearning.jwt;

import com.robosoft.elearning.modal.Role;
import com.robosoft.elearning.modal.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtUtils {

    private static final Log log = LogFactory.getLog(JwtUtils.class);
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization Header: "+ bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateTokenFromUserDetails(User user, String id) {
        UserDetails userDetails = generateUserDetails(user);

        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + jwtExpirationMs;

        return Jwts.builder()
                .claim("id", id)
                .claim("username", userDetails.getUsername())
                .claim("iat", nowMillis / 1000)
                .claim("exp", expMillis / 1000)
                .signWith(key())
                .compact();
    }

    public UserDetails generateUserDetails(User user){
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::name).toArray(String[]::new))
                .build();
    }

    public Map<String, Object> getPayloadFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload();
    }

    public String getUserNameFromJwtToken(String token) {
        return getPayloadFromJwtToken(token).get("username").toString();
    }

    public String getUserIdFromJwtToken(String token) {
        return getPayloadFromJwtToken(token).get("id").toString();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            log.info("Validating JWT token");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e) {
            handleJwtException(e, "Invalid JWT token");
        } catch (IllegalArgumentException e) {
            handleJwtException(e, "JWT claims string is empty");
        }
        return false;
    }

    public Long getUserIdFromRequestHeader(HttpServletRequest request){
        String token = getJwtFromHeader(request);
        return Long.valueOf(getUserIdFromJwtToken(token));
    }

    private void handleJwtException(Exception e, String logMessage) {
        log.error( logMessage +" "+e.getMessage());
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        request.setAttribute("jwt-error", logMessage + ": " + e.getMessage());
    }


}
