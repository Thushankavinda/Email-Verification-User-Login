package com.vertification.emailvertification.registration;

import com.vertification.emailvertification.event.RegistrationCompleteEvent;
import com.vertification.emailvertification.registration.Token.VerificationToken;
import com.vertification.emailvertification.registration.Token.VerificationTokenRepository;
import com.vertification.emailvertification.user.User;
import com.vertification.emailvertification.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Thushan Kavinda <thushankavinda95@gmail.com>
 * @since : 03/08/2024
 **/
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request) {
        User user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Sucees! please ,check your email complete your Registration";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        VerificationToken theToken = tokenRepository.findByToken(token);
        if(theToken.getUser().isEnable()){
            return "The Account Already bean Verified, Please, Login.";
        }
        String verificationResults = userService.validateToken(token);
        if (verificationResults.equalsIgnoreCase("valid")){
            return  "Email verified Successfully. Now you can Login to your Account";
        }
        return "Invalid verification token";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
