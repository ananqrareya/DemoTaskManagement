package com.ListTasks.LTasks.repository;

import com.ListTasks.LTasks.entity.TokenUtilForget;
import com.ListTasks.LTasks.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity ,Integer> {
    Optional<UserEntity>findByEmail(String email);
    Optional<UserEntity> findByTokenUtilForgets(TokenUtilForget token);
    boolean existsByEmail(String email);
}
