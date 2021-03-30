package com.example.plant_lovers.controllers;

import com.example.plant_lovers.data.*;
import com.example.plant_lovers.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private DataManagerUser dm;

    public UserController() {
        dm = new DataManagerUser();
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("hasError", false);
        return "login";
    }


    @PostMapping("/login")
    public String login(UserDTO dto, Model model) {

        var user = dm.login(dto.getUEmail(), dto.getUPassword());

        if (user == null) {
            model.addAttribute("error", "Unable to login");
            model.addAttribute("hasError", true);
            return "login";
        }

        return "your_garden";
    }


    @PostMapping("/process_register")
    public String register(UserDTO dto, Model model) {

        var user = new User(0, dto.getUEmail(), dto.getULogin(), dto.getUName(), dto.getUPassword());

        dm.addUser(user);

        return "login";
    }


    @GetMapping("/your_garden")
    public String addGarden(Model model) {

        var dm = new DataManagerGarden();


        return "your_garden";
    }

    @GetMapping("/home")
    public String back(Model model) {


        return "index";
    }
}
