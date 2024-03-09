package com.vertification.emailvertification.user;


import com.vertification.emailvertification.registration.RegistrationRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author : Thushan Kavinda <thushankavinda95@gmail.com>
 * @since : 03/08/2024
 **/
public interface IUserService {

    List<User> getUser();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);
}
