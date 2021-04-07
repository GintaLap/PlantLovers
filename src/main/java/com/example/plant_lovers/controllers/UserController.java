package com.example.plant_lovers.controllers;

import com.example.plant_lovers.SesstionData.SessionData;
import com.example.plant_lovers.data.*;
import com.example.plant_lovers.dto.GardenDTO;
import com.example.plant_lovers.dto.UserDTO;

import com.example.plant_lovers.security.Password;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private DataManagerUser dm;
    private DataManagerGarden dmg;
    private DataManagerPlants dmp;

    public UserController() {
        dm = new DataManagerUser();
        dmg = new DataManagerGarden();
        dmp = new DataManagerPlants();
    }


    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("hasError", false);
        return "login";
    }


    @PostMapping("/login")
    public String login(UserDTO udto, Model model, HttpServletRequest request) {

        var user = dm.login(udto.getUEmail());

        if (!Password.checkPassword(udto.getUPassword(), user.getPassword()) || user == null) {

            model.addAttribute("error", "Unable to login");
            model.addAttribute("hasError", true);
            return "login";
        }

        var myPlants = dmg.getYourPlants(user.getId());


        request.getSession().setAttribute(SessionData.User, user);
        model.addAttribute("user", user);
        model.addAttribute("myPlants", myPlants);

        return "your_garden";
    }


    @PostMapping("/process_register")
    public String register(UserDTO dto, Model model) {
        var newUser = new User(0, dto.getUEmail(), dto.getULogin(), dto.getUName(), dto.getUPassword());

        var users = dm.getUsers().stream().
                filter(u -> u.getEmail().equalsIgnoreCase(dto.getUEmail())).findFirst();

        if (users.isEmpty()) {

            newUser.setPassword(Password.hashPassword(newUser.getPassword()));

            dm.addUser(newUser);

            return "register_success";
        }

        model.addAttribute("error", "E-mail already been registered");
        model.addAttribute("hasMistake", true);
        return "login";
    }


    @PostMapping("/your_garden")
    public ModelAndView saveGarden(@PathVariable int id, @ModelAttribute("updateDto") GardenDTO dto, Model model) {


        return new ModelAndView("redirect:/your_garden");
    }


    @GetMapping("/home")
    public String getIndex(Model model, HttpSession session) {

        var user = (User) session.getAttribute(SessionData.User);
        model.addAttribute("user", user);


        model.addAttribute("error", "");
        model.addAttribute("hasError", false);
        return "index";
    }
}






