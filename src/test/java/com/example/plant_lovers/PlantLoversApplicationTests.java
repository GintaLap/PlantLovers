package com.example.plant_lovers;

import com.example.plant_lovers.data.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class PlantLoversApplicationTests {

    @Test
    void load_plants_from_database() {
        var dm = new DataManagerPlants();
        var res =dm.getPlants();
        Assert.notEmpty(res);

    }

//    @Test
//    public void checking_adding_new_user(){
//        var dm = new DataManagerUser();
//        User user = new User(1,"ginta@gmail.lv","gintala","Ginta","2319412no2");
//        dm.addUser(user);
//    }

    @Test
    public void check_get_user() {
        var dm = new DataManagerUser();
        var res = dm.getUsers();
        Assert.notEmpty(res);
        System.out.print(res);
    }

    @Test
    public void check_garden_view(){
        var dm = new DataManagerGarden();
        var garden = dm.getGarden();
        Assert.isTrue(garden.size() > 0);
        System.out.print(garden);
    }


}
