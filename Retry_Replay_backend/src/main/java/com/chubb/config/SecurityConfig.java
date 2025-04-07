package com.chubb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.chubb.ServiceImpl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/test").permitAll()
                .requestMatchers("/api/retries").hasAuthority("ADMIN")
                .requestMatchers("/api/replay/**").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
            .userDetailsService((UserDetailsService) userDetailsService)
            .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }
    
    
   
    @Bean
    public PasswordEncoder passwordEncoder() {
    	 BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
    	    String encodedPassword = encoder.encode("Pass@123");
    	    System.out.println("HASHED          -----"+encodedPassword);
    	    		
        return new BCryptPasswordEncoder();
    }
}