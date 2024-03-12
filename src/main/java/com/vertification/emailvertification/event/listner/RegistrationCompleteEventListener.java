package com.vertification.emailvertification.event.listner;

import com.vertification.emailvertification.event.RegistrationCompleteEvent;
import com.vertification.emailvertification.user.User;
import com.vertification.emailvertification.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author : Thushan Kavinda <thushankavinda95@gmail.com>
 * @since : 03/09/2024
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private  final  UserService userService;

    private final JavaMailSender mailSender;

    private   User theUser;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // 1. get the newly registered user
        theUser = event.getUser();
        //2. Create a  verification toke for the user
        String verificationToken = UUID.randomUUID().toString();
        //3. Save a  verification toke for the user
        userService.saveUserVerificationToken(theUser,verificationToken);
        //4.Build the verification url to be sent the user
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        //5. send the email
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("Click the link to verify your registration : {}", url);
    }
    public  void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, "+ theUser.getFirstName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("thushankavinda95@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
