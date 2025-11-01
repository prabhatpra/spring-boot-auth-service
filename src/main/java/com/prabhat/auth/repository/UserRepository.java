package com.prabhat.auth.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import com.prabhat.auth.constants.SqlQueries;
import com.prabhat.auth.pojo.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository {

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveUser(User user) {
        try (Connection conn = dataSource.getConnection()) {

            if (findByEmail(user.getEmail()).isPresent()) {
                log.warn("Email already exists: {}", user.getEmail());
                throw new IllegalStateException("Email already in use");
            }

            if (findByUserName(user.getUserName()).isPresent()) {
                log.warn("Username already exists: {}", user.getUserName());
                throw new IllegalStateException("Username already in use");
            }

            
            try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.INSERT_USER)) {
                stmt.setString(1, user.getUserName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getRole());

                stmt.executeUpdate();
                log.info("User saved successfully: {}", user.getEmail());
            }

        } catch (Exception e) {
            log.error("Error saving user: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }

    public Optional<User> findByEmail(String email) {
       

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.FIND_BY_EMAIL)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }

        } catch (Exception e) {
            log.error("Error finding user by email: {}", email, e);
        }

        return Optional.empty();
    }

    public Optional<User> findByUserName(String userName) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.FIND_BY_USERNAME)) {

            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }

        } catch (Exception e) {
            log.error("Error finding user by username: {}", userName, e);
        }

        return Optional.empty();
    }
}
