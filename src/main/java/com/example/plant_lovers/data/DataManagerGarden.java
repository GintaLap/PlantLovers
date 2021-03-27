package com.example.plant_lovers.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManagerGarden {
    private static final String conUrl = "jdbc:mysql://localhost:3306/plant_lovers?serverTimezone=UTC";

    public Integer addGarden(Garden garden) {
        Connection con = null;

        try {
            con = getConnection();

            var insertStat = con.prepareStatement(
                    "insert into garden (garden_user_id, garden_plant_id) values (?, ?)", Statement.RETURN_GENERATED_KEYS);

            insertStat.setInt(1, garden.getUser().getId());
            insertStat.setInt(2, garden.getPlant().getId());

            insertStat.executeUpdate();

            Integer id = 0;

            try (ResultSet keys = insertStat.getGeneratedKeys()) {
                keys.next();
                id = keys.getInt(1);
                garden.setId(id);
            }
            con.close();

            return id;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Garden> getGarden(){
        List<Garden> garden = new ArrayList<>();

        try {
            var con = getConnection();
            var stmt = con.createStatement();
            var rs = stmt.executeQuery("select * from v_garden_full_data");

            while (rs.next()) {
                garden.add(Garden.createGarden(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return garden;
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(conUrl, "test", "test123");
    }
}
