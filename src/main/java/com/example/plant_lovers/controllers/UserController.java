package com.example.plant_lovers.controllers;

import com.example.plant_lovers.SesstionData.SessionData;
import com.example.plant_lovers.data.*;
import com.example.plant_lovers.dto.GardenDTO;
import com.example.plant_lovers.dto.UserDTO;
import com.example.plant_lovers.models.AddGardenModel;
import com.example.plant_lovers.security.Password;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;


@Controller
@RequestMapping("/")
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
    public String profile(UserDTO udto, Model model, HttpServletRequest request) {

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


    @GetMapping("/add_garden")
    public String getSaveGarden (Model model, GardenDTO gDto, HttpSession session) {
        var user = (User)session.getAttribute(SessionData.User);
        var dataModel = new AddGardenModel();

        var plant = dmp.getPlants();
        dataModel.setPlant(plant);

        dataModel.setUserId(gDto.getUserId());

        model.addAttribute("viewModel", dataModel);

        return "add_garden";
    }

    @PostMapping("/add_garden")
    public ModelAndView saveGarden(@ModelAttribute("addGardenData") GardenDTO gDto, Model model, HttpSession session ) {
        var user = (User)session.getAttribute(SessionData.User);
          Plant plant = null;

        var res = dmp.getPlants().stream()
                .filter(p -> p.getId() == gDto.getUPlantId())
                .findFirst();

        if(res.isPresent()) {
            plant = res.get();
        }
        if (plant != null) {
            gDto.setUPlantId(plant.getId());
        }

        var gardenToAdd = new Garden(0, user.getId(), Date.valueOf(LocalDate.now()),plant);

         dmg.addGarden(gardenToAdd);

        model.addAttribute("plant", plant);
        model.addAttribute("garden", gardenToAdd);
        model.addAttribute("GardenDTO", gDto);

        return new ModelAndView("redirect:/your_garden");
    }

    @GetMapping("/calender")
    public String getGoogleChart(@PathVariable int id, Model model, HttpSession session) {

//
//        var city = dm.getCityById(id);
//
//        dm.getPopulationDataForCity(city);
//
//        model.addAttribute("city", city);
//
//        var sb = new StringBuilder();
//
//        sb.append("var graphData = [\n");
//        sb.append("['Year', 'Population'],\n");
//
//        for (var pop :
//                city.getPopulation()) {
//            sb.append("['"+ pop.getYear() +"',  "+pop.getPopulation()+"],\n");
//        }
//
//        sb.append("];");
//
//        sb.append("var cityName='"+city.getName()+"';");
//
//        model.addAttribute("graph", sb.toString());

        return "calender";
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






