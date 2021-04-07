package com.example.plant_lovers.dto;

import lombok.Data;

@Data
public class PlantDTO {
    private int id;
    private int pImageId;
    private String pScienceName;
    private String pName;
    private String pDescription;
    private String pSunlight;
    private String pMoisture;
    private int pWatering;
    private String pPetToxic;
    private String pType;
    private String pBloom;
    private String pHumidity;
}
