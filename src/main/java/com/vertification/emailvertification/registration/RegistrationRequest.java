package com.vertification.emailvertification.registration;

/**
 * @author : Thushan Kavinda <thushankavinda95@gmail.com>
 * @since : 03/08/2024
 **/
public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String role) {
}
