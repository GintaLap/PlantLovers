package com.example.plant_lovers.controllers;


import com.example.plant_lovers.data.DataManagerPlants;
import com.example.plant_lovers.models.SwitchPlantModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PlantController {
    private DataManagerPlants dm;

    public PlantController() {
        dm = new DataManagerPlants();
    }

    @GetMapping("/all_plants")
    public String getAllPlantsPage(Model model) {

        var plants = dm.getPlants();

        model.addAttribute("plants", plants);

        return "all_plants";
    }


    @GetMapping("/search_plants")
    public String searchPlants(Model model) {
        model.addAttribute("plants", dm.getPlants());
        return "details";
    }

    @PostMapping("/search_plants")
    public String findPlants(@ModelAttribute("selectedPlant") SwitchPlantModel switchPlantModel, Model model) {
        var plant = dm.getPlantById(Integer.parseInt(switchPlantModel.getSelectedPlant()));
        model.addAttribute("plant", plant);
        model.addAttribute("selectedPlant", Integer.parseInt(switchPlantModel.getSelectedPlant()));
        return "details";
    }


    @GetMapping("/about")
    public String getAboutPage(Model model) {


        return "about";
    }
}
