package com.ListTasks.LTasks.event;

import com.ListTasks.LTasks.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserForgetTokenEventPublisher {
    @Autowired
    private ApplicationEventPublisher publisher;
    public void setPublisher(UserEntity user, String token) {

        UserForgetTokenEvent event = new UserForgetTokenEvent(this, user.getEmail(),token );
        publisher.publishEvent(event);
    }
}
