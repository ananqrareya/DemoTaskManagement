package com.ListTasks.LTasks.api;


import com.ListTasks.LTasks.Dto.LoginDto;
import com.ListTasks.LTasks.Dto.RegisterDTO;

import com.ListTasks.LTasks.Dto.UserDto;
import com.ListTasks.LTasks.event.UserForgetTokenEventPublisher;
import com.ListTasks.LTasks.exceptionhandler.RegisterUserExceptions;
import com.ListTasks.LTasks.entity.UserEntity;
import com.ListTasks.LTasks.repository.UserEntityRepository;
import com.ListTasks.LTasks.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
@Autowired
private UserService userService;
@Autowired
private UserEntityRepository userEntityRepository;
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private UserForgetTokenEventPublisher passwordEventPublisher;
@PostMapping("/register")
    public ResponseEntity<?>registerUser(@RequestBody RegisterDTO registerDTO){
    try {
        RegisterDTO user=userService.newUser(registerDTO);

        return ResponseEntity.ok().body("The Register Successfully :"+user);
    }catch (RegisterUserExceptions e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering user: " + e.getMessage());

    }
}
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto , HttpSession session) {
        Authentication existingAuth= SecurityContextHolder.getContext().getAuthentication();
        if(existingAuth!=null&&existingAuth.isAuthenticated()&&!(existingAuth instanceof AnonymousAuthenticationToken)){
            return new ResponseEntity<>("User is already logged in!", HttpStatus.CONFLICT);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return new ResponseEntity<>("User login successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>("User logged out successfully!", HttpStatus.OK);
    }


    @PostMapping("/forget")
    public ResponseEntity<?> generateTokenForget(@RequestBody Map<String, String> request){
        String email=request.get("email");
        try {
            userService.generatedToken(email);
            return new ResponseEntity<>("Verification code sent to your email", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Invalid email", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<?>verifyToken(@RequestBody Map <String, String> request){
        String token=request.get("token");
        try {
            UserEntity user=userService.verifyToken(token);
            if(user!=null){
                return   ResponseEntity.ok("Token verified");
            }
            else {
                return ResponseEntity.ok("verification failed.");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/change-password")
    public ResponseEntity<?>changePassword(@RequestBody Map<String ,String>request ){

        String email = request.get("email");
        String password = request.get("password");
        System.out.println(password);
        try {
            userService.updatePassword(email, password);
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        }
        @GetMapping("/information")
    public ResponseEntity<?>getInformationUser(){
            UserEntity user=userService.getAuthenticatedUser();
            if(user==null){
                throw new UsernameNotFoundException("Not Found User");
            }
            return ResponseEntity.ok(user);
        }
        @PostMapping("/updateUser")
    public ResponseEntity<?>updateUser(@RequestBody UserDto userDto){
        UserEntity user=userService.getAuthenticatedUser();
            System.out.println(user.getFullName());
            System.out.println(userDto.getFullName());
        try {
            userService.updateUser(user, userDto);
            return ResponseEntity.ok("User Update successfully");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
}




}
