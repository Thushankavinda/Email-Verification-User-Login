package com.vertification.emailvertification.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : Thushan Kavinda <thushankavinda95@gmail.com>
 * @since : 03/08/2024
 **/

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
