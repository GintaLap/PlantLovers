package com.example.plant_lovers.controllers;

import com.example.plant_lovers.SesstionData.SessionData;
import com.example.plant_lovers.data.*;
import com.example.plant_lovers.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String login(UserDTO udto, Model model, HttpServletRequest request) {

        var user = dm.login(udto.getUEmail(), udto.getUPassword());

        if (user == null) {
            model.addAttribute("error", "Unable to login");
            model.addAttribute("hasError", true);
            return "login";
        }
        request.getSession().setAttribute(SessionData.User, user);
        model.addAttribute("user", user);
        return "your_garden";
    }

    @GetMapping("/your_garden")
    public String getGarden(Model model, HttpSession session) {
       var user = (User)session.getAttribute(SessionData.User);
        model.addAttribute("user", user);


        return "your_garden";
    }


    @PostMapping("/process_register")
    public String register(UserDTO dto, Model model) {
        var user = new User(0, dto.getUEmail(), dto.getULogin(), dto.getUName(), dto.getUPassword());

        if (!user.getEmail().equalsIgnoreCase(dto.getUEmail())) {
            dm.addUser(user);
            return "register_success";
        }
        model.addAttribute("error", "E-mail already been registered");
        model.addAttribute("hasMistake", true);
        return "login";
    }


//    @GetMapping("/your_garden")
//    public String addGarden(Model model) {
//        var dmg = new DataManagerGarden();
//        var garden = dmg.getGarden();
//        model.addAttribute("addedGarden", garden);
//        var user = dm.getUsers();
//        model.addAttribute("user", user);
//        dmg.addGarden(garden);
//        return "your_garden";
//    }


    @GetMapping("/home")
    public String getIndex(Model model) {
        model.addAttribute("error","");
        model.addAttribute("hasError", false);
        return "index";
    }
}
