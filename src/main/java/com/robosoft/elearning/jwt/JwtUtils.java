package com.robosoft.elearning.jwt;

import com.robosoft.elearning.exception.JwtException;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.modal.Role;
import com.robosoft.elearning.modal.User;
//import io.jsonwebtoken.JwtException;
import com.robosoft.elearning.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    private static final Log log = LogFactory.getLog(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtAccessExpirationMs}")
    private int jwtAccessExpirationMs;

    @Value("${spring.app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserRepository userRepository;


    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization Header: "+ bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateAccessToken(User user) {
        UserDetails userDetails = generateUserDetails(user);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + jwtAccessExpirationMs;
        return Jwts.builder()
                .claim("sub", user.getId().toString())
                .claim("username", userDetails.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::name).toList())
                .claim("iat", nowMillis / 1000)
                .claim("exp", expMillis / 1000)
                .claim("type", "access")
                .signWith(key())
                .compact();
    }

    public String generateRefreshToken(User user) {
        UserDetails userDetails = generateUserDetails(user);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + jwtRefreshExpirationMs;
        return Jwts.builder()

                .claim("sub", user.getId().toString())
                .claim("tokenType", "refresh")
                .claim("iat", nowMillis / 1000)
                .claim("exp", expMillis / 1000)
                .claim("type", "refresh")
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
        return getPayloadFromJwtToken(token).get("sub").toString();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        if (isTokenBlacklisted(authToken)) {
            log.error("Token is blacklisted");
            return false;
        }
        try {
            log.info("Validating JWT token");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e) {
            throw new com.robosoft.elearning.exception.JwtException("Invalid JWT token: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new com.robosoft.elearning.exception.JwtException("JWT claims string is empty: " + e.getMessage(), e);
        }
    }

    public Long getUserIdFromRequestHeader(HttpServletRequest request){
        String token = getJwtFromHeader(request);
        return Long.valueOf(getUserIdFromJwtToken(token));
    }

    public void blackListRefreshToken(String token) {
        stringRedisTemplate.opsForValue().set("blacklist:" + token, token, jwtRefreshExpirationMs, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        return stringRedisTemplate.hasKey("blacklist:" + token);
    }

    public void blackListAccessToken(String token){
        stringRedisTemplate.opsForValue().set("blacklist:" + token, token, jwtAccessExpirationMs, TimeUnit.MILLISECONDS);
    }

    public boolean isAccessToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return "access".equals(claims.get("type"));
    }

    public boolean isRefreshToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return "refresh".equals(claims.get("type"));
    }

    public User getUserDataFromRequest(HttpServletRequest request) {
        // Step 1: Get the JWT token from the request header
        String token = getJwtFromHeader(request);

        // Step 2: Validate the token (you can use your existing validateJwtToken method)
        if (!validateJwtToken(token)) {
            throw new JwtException("Invalid JWT token");
        }

        // Step 3: * user ID from the token
        Long userId = Long.parseLong(getUserIdFromJwtToken(token));

        // Step 4: Fetch the user data from the database using the user ID
        // Assuming you have a method to fetch the user by ID. You can use a repository or a service.
        User user = getUserById(userId);

        return user;  // Return the user object
    }

    private User getUserById(Long userId) {
        // Replace this with your actual method to fetch the user from the repository.
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


}



//                .claim("jti", UUID.randomUUID().toString())
