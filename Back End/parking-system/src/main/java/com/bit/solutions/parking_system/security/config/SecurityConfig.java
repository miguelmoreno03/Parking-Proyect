package com.bit.solutions.parking_system.security.config;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.bit.solutions.parking_system.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        //Login
                        .requestMatchers("/auth/**").permitAll()
                        //Users
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        //Rates
                        .requestMatchers(HttpMethod.GET,"/rates/**").hasAnyRole("ADMIN","OPERATOR")
                        .requestMatchers(HttpMethod.POST, "/rates/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/rates/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/rates/**").hasRole("ADMIN")
                        //RECORDS
                        .requestMatchers(HttpMethod.GET, "/records/**").hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers(HttpMethod.POST, "/records").hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers(HttpMethod.POST, "/records/exit/**").hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers(HttpMethod.PATCH, "/records/**").hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers(HttpMethod.DELETE, "/records/**").hasRole("ADMIN")
                        //CONFIG
                        .requestMatchers(HttpMethod.GET, "/config/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/config/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/config/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/config/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

