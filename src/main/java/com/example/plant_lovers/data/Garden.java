package com.example.plant_lovers.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "garden")
public class Garden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "garden_id")
    private Integer id;
    @Column(name = "garden_user_id")
    private Integer userId;
    @Column(name = "garden_plant_id")
    private Integer plantId;


}
