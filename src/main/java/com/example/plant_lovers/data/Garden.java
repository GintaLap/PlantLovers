package com.example.plant_lovers.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


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

    @Column(name = "garden_plant_watering_date")
    private Date waterDate;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "garden_plant_id",referencedColumnName = "plant_id")
    private Plant plant;


}
