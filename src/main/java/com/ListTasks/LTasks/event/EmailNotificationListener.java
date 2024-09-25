package com.ListTasks.LTasks.event;

import com.ListTasks.LTasks.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener {
    @Autowired
    private EmailSender emailSender;
    @EventListener

    public void handleUserAddedEvent(UserForgetTokenEvent event) {
        String subject = "Forget Password ? ";
        String verificationToken =event.getToken();
        String message ="Your 6 Digit Verification Code : "+verificationToken;
        String to = event.getEmail();

        emailSender.setMailSender(to, subject, message);

    }
}
