package com.robosoft.elearning.config;

import com.robosoft.elearning.jwt.AuthEntryPointJwt;
import com.robosoft.elearning.jwt.AuthTokenFilter;
import com.robosoft.elearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.stream.Collectors;


//package com.robosoft.elearning.config;

import com.robosoft.elearning.jwt.AuthEntryPointJwt;
import com.robosoft.elearning.jwt.AuthTokenFilter;
import com.robosoft.elearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(
                                        "/api/v1/user/send-reg-otp",
                                "/api/v1/admin/send-reg-otp",
                                "/api/v1/user/register",
                                "/api/v1/admin/register",
                                "/api/v1/login",
                                "/api/v1/refresh-Token"
                        ).permitAll()
                        .anyRequest().authenticated()
        );
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(unauthorizedHandler)
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .map(user -> {
                    var authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                            .collect(Collectors.toList());

                    return new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            authorities);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}


//@Configuration
//@EnableWebSecurity
//public class AppConfig {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private AuthEntryPointJwt unauthorizedHandler;
//
//    @Bean
//    public AuthTokenFilter authenticationJwtTokenFilter() {
//        return new AuthTokenFilter();
//    }
//
//
//    @Bean
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/api/user/v1/send-reg-otp","/api/admin/v1/send-reg-otp","/api/v1/user/register","/api/v1/admin/register").permitAll()
//                                .anyRequest().authenticated()
//        );
//        http.sessionManagement(session ->
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
//
//        http.exceptionHandling(exception ->
//                exception.authenticationEntryPoint(unauthorizedHandler)
//        );
//
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return email -> userRepository.findByEmail(email)
//                .map(user -> {
//                    var authorities = user.getRoles().stream()
//                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                            .collect(Collectors.toList());
//
//                    return new org.springframework.security.core.userdetails.User(
//                            user.getEmail(),
//                            user.getPassword(),
//                            authorities);
//                })
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
//        return builder.getAuthenticationManager();
//    }
//
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
//        return new StringRedisTemplate(jedisConnectionFactory);
//    }
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory(@Value("${spring.redis.host}") String redisHost,
//                                                         @Value("${spring.redis.port}") int redisPort,
//                                                         @Value("${spring.redis.password}") String redisPassword) {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(redisHost);
//        jedisConnectionFactory.setPort(redisPort);
//        jedisConnectionFactory.setPassword(redisPassword);
//        return jedisConnectionFactory;
//    }
//
//
//}
