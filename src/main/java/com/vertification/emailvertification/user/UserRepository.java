package com.vertification.emailvertification.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Thushan Kavinda <thushankavinda95@gmail.com>
 * @since : 03/08/2024
 **/
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
