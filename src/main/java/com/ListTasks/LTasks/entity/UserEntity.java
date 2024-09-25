package com.ListTasks.LTasks.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name="user")
@Entity
@Schema(description = "User entity representing a user in the system")

@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1", required = true)
    private int id;
    @Schema(description = "Username of the user", example = "john_doe", required = true)
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email")
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String email;
    @Column(name = "password")
    @Schema(description = "Password of the user", example = "P@ssw0rd", required = true)
    private String password ;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<TokenUtilForget>tokenUtilForgets=new ArrayList<>();
@JsonBackReference
    @OneToMany(mappedBy ="user",cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Task>tasks=new ArrayList<>();
    @Override
    public String toString() {
        return "UserEntity{id=" + id + ", username='" + email + "'}";
    }

}
