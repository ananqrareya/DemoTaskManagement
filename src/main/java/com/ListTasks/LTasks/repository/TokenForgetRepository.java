package com.ListTasks.LTasks.repository;

import com.ListTasks.LTasks.entity.TokenUtilForget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TokenForgetRepository extends JpaRepository<TokenUtilForget,Integer> {
    TokenUtilForget findByToken(String token);
}
