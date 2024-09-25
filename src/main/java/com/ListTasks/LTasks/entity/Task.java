package com.ListTasks.LTasks.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(name = "category_task")
    private String categoryTask ;
    @JsonManagedReference
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity user;
@JsonManagedReference
    @OneToMany(mappedBy = "category",cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<SubTask>subTasks=new ArrayList<>();


    @Override
    public String toString() {
        return "Task{id=" + id + ", categoryTask='" + categoryTask + "'}";
    }
}
