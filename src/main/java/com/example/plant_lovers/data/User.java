package com.example.plant_lovers.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import java.sql.ResultSet;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String login;
    private String name;
    private String password;


    @SneakyThrows
    public static User createUser(ResultSet rs) {

        var user = new User(rs.getInt("user_id"),
                rs.getString("user_login"),
                rs.getString("user_name"),
                rs.getString("user_password"));

        return user;
    }
}
