package com.example.plant_lovers.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(unique = true, name = "user_email")
    private String email;
    @Column(name = "user_login")
    private String login;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_password")
    private String password;

}
