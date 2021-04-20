package com.example.plant_lovers.controllers;

import com.example.plant_lovers.SesstionData.SessionData;
import com.example.plant_lovers.data.*;
import com.example.plant_lovers.dto.GardenDTO;
import com.example.plant_lovers.dto.UserDTO;
import com.example.plant_lovers.models.AddGardenModel;
import com.example.plant_lovers.security.Password;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/")
public class UserController {
    private DataManagerUser dm;
    private DataManagerGarden dmg;
    private DataManagerPlants dmp;
    private static final String FILE_PATH = "src/main/resources/calendar_data.json";

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
        dmg.saveToJason(user.getId());

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


    @GetMapping("/home")
    public String getIndex(Model model, HttpSession session) {

        var user = (User) session.getAttribute(SessionData.User);
        model.addAttribute("user", user);


        model.addAttribute("error", "");
        model.addAttribute("hasError", false);
        return "index";
    }

    @GetMapping("/your_garden")
    public String getGarden(Model model, HttpSession session) {
        var user = (User) session.getAttribute(SessionData.User);

        var myPlants = dmg.getYourPlants(user.getId());


        model.addAttribute("user", user);
        model.addAttribute("myPlants", myPlants);

        return "your_garden";
    }

    @GetMapping("/add_garden")
    public String getSaveGarden(Model model, GardenDTO gDto, HttpSession session) {
        var user = (User) session.getAttribute(SessionData.User);

        var dataModel = new AddGardenModel();

        var plant = dmp.getPlants();
        dataModel.setPlant(plant);
        dataModel.setUserId(user.getId());

        model.addAttribute("viewModel", dataModel);

        return "add_garden";
    }

    @PostMapping("/add_garden")
    public ModelAndView saveGarden(@ModelAttribute("addGardenData") GardenDTO gDto, Model model, HttpSession session) {
        var user = (User) session.getAttribute(SessionData.User);

        Plant plant = null;

        var res = dmp.getPlants().stream()
                .filter(p -> p.getId() == gDto.getUPlantId())
                .findFirst();

        if (res.isPresent()) {
            plant = res.get();
        }
        if (plant != null) {
            gDto.setUPlantId(plant.getId());
        }

        var gardenToAdd = new Garden(0, user.getId(), LocalDateTime.now(), plant);
        dmg.addGarden(gardenToAdd);

        dmg.saveToJason(user.getId());

        model.addAttribute("plant", plant);
        model.addAttribute("garden", gardenToAdd);
        model.addAttribute("GardenDTO", gDto);

        return new ModelAndView("redirect:/your_garden");
    }

    @GetMapping("/delete_garden")
    public String getDeletePlant(GardenDTO gDto, Model model, HttpSession session) {
        var user = (User) session.getAttribute(SessionData.User);

        var myPlants = dmg.getYourPlants(user.getId());

        var dataModelD = new AddGardenModel();
        dataModelD.setPlant(myPlants);
        dataModelD.setUserId(user.getId());


        model.addAttribute("viewModelD", dataModelD);
        model.addAttribute("user", user);

        return "delete_garden";
    }

    @PostMapping("/delete_garden")
    public ModelAndView deleteGarden(@ModelAttribute("addGardenData") GardenDTO gDto, Model model, HttpSession session) {
        var user = (User) session.getAttribute(SessionData.User);


        var removeGarden = dmg.getGarden().stream()
                .filter(g -> g.getUserId() == user.getId() && g.getPlant().getId() == gDto.getDPlantId())
                .findFirst();
        var rGardenId = removeGarden.get().getId();
        dmg.deleteGarden(rGardenId);

        dmg.saveToJason(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("GardenDTO", gDto);

        return new ModelAndView("redirect:/your_garden");
    }


    @GetMapping(value = "/calendar")
    public ModelAndView getCalendar(HttpSession session) {
        var user = (User) session.getAttribute(SessionData.User);
        ModelAndView modelAndView = new ModelAndView("calendar");


        return modelAndView;
    }

    @RequestMapping(value = "/calendar/dates", method = RequestMethod.GET)
    public
    @ResponseBody
    String plantCalendar(HttpSession session, HttpServletResponse response) throws IOException {
        var user = (User) session.getAttribute(SessionData.User);

        var dmg = new DataManagerGarden();
        var myGarden = dmg.getGarden().stream().
                filter(g -> (g.getUserId().equals(user.getId())))
                .collect(Collectors.toList());


        var l = dmg.getCalendarDataForJson(user.getId());

        // Convert to JSON string.
        String json = new Gson().toJson(l);

        // Write JSON string.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        return json;
    }
}






