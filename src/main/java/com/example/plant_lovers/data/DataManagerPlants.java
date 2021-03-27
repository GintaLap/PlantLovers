package com.example.plant_lovers.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManagerPlants {
    private static final String conUrl = "jdbc:mysql://localhost:3306/plant_lovers?serverTimezone=UTC";


    public List<Plant> getPlants() {
        List<Plant> plants = new ArrayList<>();

        try {
            var con = getConnection();
            var stmt = con.createStatement();
            var rs = stmt.executeQuery("select * from plants");

            while (rs.next()) {
                plants.add(Plant.createPlant(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return plants;
    }



    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(conUrl, "test", "test123");
    }
}
