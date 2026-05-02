package com.prabhat.auth.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prabhat.auth.entity.ResetToken;
import com.prabhat.auth.entity.User;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
	
	Optional<ResetToken> findByToken(String token);
	
	void deleteByUser(User user);
}
