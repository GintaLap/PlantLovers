package com.example.plant_lovers.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.ResultSet;

@Data
@AllArgsConstructor
public class Garden {
    private Integer id;
    private User user;
    private Plant plant;

    @SneakyThrows
    public static Garden createGarden(ResultSet rs){

        var garden = new Garden(rs.getInt("garden_id"),
                User.createUser(rs),
                Plant.createPlant(rs));
        return garden;
    }

}
