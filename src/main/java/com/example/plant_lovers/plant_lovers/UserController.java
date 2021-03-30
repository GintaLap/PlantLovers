package com.example.plant_lovers.plant_lovers;

import com.example.plant_lovers.data.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;


@Controller
public class UserController {


    @GetMapping("/login")
    public String toLogIn(Model model) {
        String login = "";
        String password = "";
        var dmu = new DataManagerUser();
        var user =  dmu.verifyUser(login, password);

        model.addAttribute("user" , user);
        var dmp = new DataManagerPlants();
        var plant = dmp.getPlants();
        model.addAttribute("plant", plant);

        return "login";
    }



    //needs to do some fixing, not working at the moment
    @PostMapping("/login/register")
    public ModelAndView addUser(@ModelAttribute("addUserData") UserDTO dto) {
        var dm = new DataManagerUser();

        var user = new User(0, dto.getULogin(), dto.getUName(), dto.getUPassword());

        dm.addUser(user);

        return new ModelAndView("login");
    }


        @GetMapping("/your_garden")
    public ModelAndView addGarden(@ModelAttribute("addGardenData") UserDTO dto) {

        var dm = new DataManagerGarden();



        return new ModelAndView("your_garden");
    }


    @GetMapping("/home")
    public String back(Model model) {


        return "index";
    }
}
