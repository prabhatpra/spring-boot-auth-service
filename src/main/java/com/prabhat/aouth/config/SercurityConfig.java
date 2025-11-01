package com.prabhat.aouth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SercurityConfig {

	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
		 http.csrf().disable()
		 .authorizeHttpRequests()
		 .requestMatchers("/user/**").permitAll()
		 .anyRequest().authenticated();
		 
		 return http.build();
	 }
	 
	  @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
}
