package com.example.plant_lovers.data;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String name;
    private String password;
    private int userPlantId;




}
