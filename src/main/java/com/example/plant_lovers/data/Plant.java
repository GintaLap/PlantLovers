package com.example.plant_lovers.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private int id;
    @Column(name = "plant_image_id")
    private int imageId;
    @Column(name = "plant_scientific_name")
    private String scienceName;
    @Column(name = "plant_name")
    private String name;
    @Column(name = "plant_description")
    private String description;
    @Column(name = "plant_sunlight")
    private String sunlight;
    @Column(name = "plant_moisture")
    private String moisture;
    @Column(name = "plant_watering")
    private int watering;
    @Column(name = "plant_toxic")
    private String petToxic;
    @Column(name = "plant_type")
    private String type;
    @Column(name = "plant_bloom")
    private String bloom;
    @Column(name = "plant_humidity")
    private String humidity;

    public String image() {
        return "./img/" + imageId + ".jpg";
    }

}
