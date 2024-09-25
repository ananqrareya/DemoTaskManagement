package com.ListTasks.LTasks.service;

import com.ListTasks.LTasks.Dto.RegisterDTO;
import com.ListTasks.LTasks.Dto.UserDto;
import com.ListTasks.LTasks.entity.TokenUtilForget;
import com.ListTasks.LTasks.entity.UserEntity;
import com.ListTasks.LTasks.event.UserForgetTokenEventPublisher;
import com.ListTasks.LTasks.exceptionhandler.TokenExpiredException;
import com.ListTasks.LTasks.exceptionhandler.TokenNotFoundException;
import com.ListTasks.LTasks.repository.TokenForgetRepository;
import com.ListTasks.LTasks.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;

@Service
public class UserService {
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private TokenForgetRepository tokenForgetRepository;
    @Autowired
    private UserForgetTokenEventPublisher publisher;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final SecureRandom secureRandom = new SecureRandom();

    public RegisterDTO newUser(RegisterDTO userDto) {
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new RuntimeException("Password cannot be null or empty");
        }
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new RuntimeException( "Email already Exists");
        }
        UserEntity user=new UserEntity();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return userDto;
    }

    public void generatedToken(String email) {
        UserEntity user=userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Not Found User"));
        int digit = secureRandom.nextInt(900000) + 100000; // generates a random number between 100000 and 999999
        String token=String.valueOf(digit);
        TokenUtilForget tokenForgetPass=new TokenUtilForget();
        tokenForgetPass.setToken(token);
        tokenForgetPass.setTokenExpirationDate(new Timestamp(System.currentTimeMillis() + 86400000));
        tokenForgetPass.setUser(user);
        tokenForgetRepository.save(tokenForgetPass);
        publisher.setPublisher(user,token);
    }

    public UserEntity verifyToken(String token) {
        TokenUtilForget tokenForgetPass=tokenForgetRepository.findByToken(token);
        System.out.println(token);
        UserEntity user=userRepository.findByTokenUtilForgets(tokenForgetPass).orElseThrow(()->new RuntimeException("Token not found."));;

        if (tokenForgetPass.getTokenExpirationDate().before(new Timestamp(System.currentTimeMillis()))) {
            throw new TokenExpiredException("Token has expired.");
        }
        tokenForgetPass.setToken(null);
        tokenForgetPass.setTokenExpirationDate(null);
        tokenForgetRepository.save(tokenForgetPass);
        return user;
    }
    public  UserEntity getAuthenticatedUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        UserEntity user =userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("Not Found User the "));
        return user;
    }
    public void updatePassword(String email, String password) {
        UserEntity user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        user.setPassword(passwordEncoder.encode(password));
          userRepository.save(user);
    }

    public void updateUser(UserEntity user, UserDto userDto) {
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }
}
