package com.ListTasks.LTasks.security;

import com.ListTasks.LTasks.entity.UserEntity;
import com.ListTasks.LTasks.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private UserEntityRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user=userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));
        return new User(user.getEmail(),user.getPassword(),authorities);
    }
}
