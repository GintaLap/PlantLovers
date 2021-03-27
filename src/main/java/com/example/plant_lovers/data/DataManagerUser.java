package com.example.plant_lovers.data;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManagerUser {
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/plant_lovers?serverTimezone=UTC";

    public List<User> verifyUser(String login, String password) { // this method has to be corrected and finalized
        List<User> user = new ArrayList<>();

        try {
            var con = getConnection();
            var stat = con.createStatement();
         //   var res = stat.executeQuery("select * from user");
            var res = stat.executeQuery("select * from user where user_login = '' and user_password = ''");

        //    if ((password == res.getString("user_password")) && (login == res.getString("user_login"))) {

                while (res.next() && ( (password == res.getString("user_password")) && (login == res.getString("user_login")) )) {
                    var userLog = new User(res.getInt("user_id"),
                            res.getString("user_login"),
                            res.getString("user_name"),
                            res.getString("user_password"),
                            res.getInt("user_plant_id"));


                    if ( (login == res.getString("user_login")) && (password == res.getString("user_password")) ) {
                        System.out.println(userLog + " userlog");
                        user.add(userLog);
                    }

//                    System.out.println(userLog + " userlog");
//                    user.add(userLog);
                   // user = userLog; // if there would be Lists, then user.add(userLog);
                }
       //     }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(user + "user");
        return user;
    }


    @Test
    public void check_password(){
        System.out.println(verifyUser("maija", "123456")); //passing by the correct result and gives empty object..
     //   verifyUser("kautkas", "1d11s1s");
    }










//    public User verifyUser() { // this method has to be corrected and finalized
//       User user = new User();
//
//        try {
//            var con = getConnection();
//            var stat = con.createStatement();
//            var res = stat.executeQuery("select * from user");
//
//            while ((checkPassword(user.getLogin(), user.getPassword()) == true) && res.next()) {
//                var userLog = new User(res.getInt("user_id"),
//                        res.getString("user_login"),
//                        res.getString("user_name"),
//                        res.getString("user_password"),
//                        res.getInt("user_plant_id"));
//
//                user = userLog; // if there would be Lists, then user.add(userLog);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return user;
//    }
//
//
//    private boolean checkPassword(String login, String password) {
//        User user = new User();
//     //   int counter = 5;
//        while ((password != user.getPassword()) && (login != user.getLogin())) ;
//   //     counter--;
//        if ((password == user.getPassword()) && (login == user.getLogin())) { //counter >= 1 &&
//            getUserPlants(login);
//            return true;
//        } else {
//            getSupport();
//            return false;
//        }
//    }
//
//    private String getSupport() {
//        String msg = "Ups! Your password seems wrong. Get in touch with our support team";
//        return msg;
//    }
//
//
//    @Test
//    public void check_password(){
//        checkPassword("maija", "123456" ); // takes huge time to process, obviously something wrong
//     //   verifyUser();
//    }


    public Integer addUser(User user) {
        Connection con = null;
        try {
            con = getConnection();

            var insertStat = con.prepareStatement(
                    "insert into user (user_login, user_name, user_password) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            insertStat.setString(1, user.getLogin());
            insertStat.setString(2, user.getName());
            insertStat.setString(3, user.getPassword());

            insertStat.executeUpdate();

            Integer id = 0;

            try (ResultSet keys = insertStat.getGeneratedKeys()) {
                keys.next();
                id = keys.getInt(1);
                user.setId(id);
            }
            con.close();

            return id;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    @Test
//    public void checking_adding_new_user(){
//        User user = new User(null,"lorie", "lorie brown", "654321", null);
//        addUser(user);
//    }


    public List<User> getUser() {
        List<User> users = new ArrayList<>();

        try {
            var con = getConnection();
            var sat = con.createStatement();
            var rs = sat.executeQuery("SELECT n. * FROM user as n");

            while (rs.next()) {
                users.add(User.create(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }


//    @Test
//    public void check_get_user() {
//        var users = getUser();
//        Assert.assertTrue(users.size() > 0);
//        System.out.print(users);
//    }


    public Integer addUserPlants(Plant plant, String userLogin) { // there is error in inserting
             User user = new User();

        Connection con = null;
        try {
            con = getConnection();

            var insertStat = con.prepareStatement(
                    "insert into user (user_login, user_plant_id) values (?,?)", Statement.RETURN_GENERATED_KEYS);

            insertStat.setString(1, user.getLogin());
            insertStat.setInt(2,user.getUserPlantId());

            insertStat.executeUpdate();

            Integer id = 0;

            try (ResultSet keys = insertStat.getGeneratedKeys()) {
                keys.next();
                id = keys.getInt(5);
                user.setId(id);
            }

            con.close();

            return id;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Test
    public void plants_can_be_added() { // there is  error
        Plant plant = new Plant(1,2,3 , "", "Medium", "Part shade to full shade", 7, false, "Broadleaf evergreen", "Late Summer to Fall");
       //  addUserPlants( "Aphelandra squarrosa", "Zebra Plant"
        addUserPlants(plant,"rico");

    }


    public User getUserPlants(String login) {
        try {
            var con = getConnection();
            var stat = con.prepareStatement("select * from user where user_login = ?");

            stat.setString(1, login);

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                return User.create(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

//    @Test
//    public void check_if_getting_user_plants (){
//        System.out.println(getUserPlants("maija").getUserPlantId());
//    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, "test", "test123");
    }
}

