package com.vertification.emailvertification.user;

import com.vertification.emailvertification.exception.UserAlreadyExistsException;
import com.vertification.emailvertification.registration.RegistrationRequest;
import com.vertification.emailvertification.registration.Token.VerificationToken;
import com.vertification.emailvertification.registration.Token.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * @author : Thushan Kavinda <thushankavinda95@gmail.com>
 * @since : 03/08/2024
 **/
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if(user.isPresent()){
            throw new UserAlreadyExistsException(
                    "User with Email"+request.email()+ "alreadyExists");
        }
        var newUser =  new User();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return  userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token,theUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String token) {
        VerificationToken  vtoken =  tokenRepository.findByToken(token);
        if (token == null){
            return "Invalid verification token";
        }
        User user = vtoken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((vtoken.getTokenExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(vtoken);
            return "Token already Expired";
        }
        user.setEnable(true);
        userRepository.save(user);
        return "valid";
    }


}
