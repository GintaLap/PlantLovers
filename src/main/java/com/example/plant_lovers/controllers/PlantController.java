package com.example.plant_lovers.controllers;
import com.example.plant_lovers.data.DataManagerPlants;
import com.example.plant_lovers.data.Plant;
import com.example.plant_lovers.dto.UserDTO;
import com.example.plant_lovers.models.SwitchPlantModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;


@Controller
public class PlantController {
    private DataManagerPlants dm;

    public PlantController(){
        dm =new DataManagerPlants();
    }

    @GetMapping("/all_plants")
    public String getAllPlantsPage(Model model) {

        var plants = dm.getPlants();

        model.addAttribute("plants", plants);

        return "all_plants";
    }

    @PostMapping("/search_plants")
    public String searchPlant( Model model) {
     //   var plant = dm.getPlantById(id);
        model.addAttribute("plants", dm.getPlants());

            return "details";
        }



    @GetMapping("/about")
    public String getAboutPage(Model model) {


        return "about";
    }
}
