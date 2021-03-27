package com.example.plant_lovers.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.ResultSet;

@Data
@AllArgsConstructor
public class Plant {
    private int id;
    private int imageId;
    private String scienceName;
    private String name;
    private String description;
    private String sunlight;
    private String moisture;
    private int watering;
    private String petToxic;
    private String type;
    private String humidity;

    @SneakyThrows
    public static Plant createPlant(ResultSet rs) {

        var plant = new Plant(rs.getInt("plant_id"),
                rs.getInt("plant_image_id"),
                rs.getString("plant_scientific_name"),
                rs.getString("plant_name"),
                rs.getString("plant_description"),
                rs.getString("plant_moisture"),
                rs.getString("plant_sunlight"),
                rs.getInt("plant_watering"),
                rs.getString("plant_toxic"),
                rs.getString("plant_type"),
                rs.getString("plant_humidity"));
        return plant;
    }

}
