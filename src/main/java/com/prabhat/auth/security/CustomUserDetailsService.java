package com.prabhat.auth.security;

import com.prabhat.auth.pojo.User;
import com.prabhat.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(emailOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + emailOrUsername));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),                         // Username
                user.getPassword(),                     // Encoded password
                Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole()) // Role like ROLE_USER, ROLE_ADMIN
                )
        );
    }
}
