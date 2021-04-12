package com.example.plant_lovers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GardenDTO {
    private Integer id;
    private Integer UserId;
    private Integer uPlantId;


}
