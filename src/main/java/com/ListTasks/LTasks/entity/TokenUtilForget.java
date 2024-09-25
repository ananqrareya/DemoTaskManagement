package com.ListTasks.LTasks.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "forget_token")
@Data
@NoArgsConstructor
public class TokenUtilForget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(name = "token")
    private String token ;
    @Column(name = "token_expiration_date")
    private Timestamp tokenExpirationDate;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
