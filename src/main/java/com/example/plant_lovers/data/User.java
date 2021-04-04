package com.example.plant_lovers.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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



//    One approach
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private List<Garden> gardens;

    // Second approach
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "garden",
//            joinColumns =
//                    { @JoinColumn(name = "garden_user_id", referencedColumnName = "user_id") },
//            inverseJoinColumns =
//                    { @JoinColumn(name = "garden_plant_id", referencedColumnName = "plant_id") })
//    private Plant plant;
}
