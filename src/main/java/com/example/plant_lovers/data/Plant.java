package com.example.plant_lovers.data;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Plant {
    private int id;
    private int scienceName;
    private int name;
    private String description;
    private String sunlight;
    private String moisture;
    private int watering;
    private boolean petToxic;
    private String type;
    private String humidity;

}
