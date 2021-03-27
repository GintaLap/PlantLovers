package com.example.plant_lovers.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String login;
    private String name;
    private String password;
    private Integer userPlantId;

    @SneakyThrows
    public static User create(ResultSet rs) {

        var user = new User (rs.getInt("user_id"),
                rs.getString("user_login"),
                rs.getString("user_name"),
                rs.getString("user_password"),
                rs.getInt("user_plant_id"));

        return user;



    }


}
