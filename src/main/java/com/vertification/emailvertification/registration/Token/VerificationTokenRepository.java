package com.vertification.emailvertification.registration.Token;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author : Thushan Kavinda <thushankavinda95@gmail.com>
 * @since : 03/09/2024
 **/

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}
