package com.example.plant_lovers.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WateringEventModel {

    private String title;
    private String start;
    private String allDay;

}
