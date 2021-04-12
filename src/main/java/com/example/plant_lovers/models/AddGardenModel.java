package com.example.plant_lovers.models;

import com.example.plant_lovers.data.Plant;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
public class AddGardenModel {
    private Integer userId;
    private List<Plant> plant;
}
