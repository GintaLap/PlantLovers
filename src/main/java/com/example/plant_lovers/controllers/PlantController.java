package com.example.plant_lovers.controllers;
import com.example.plant_lovers.data.DataManagerPlants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


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

    @GetMapping("/about")
    public String getAboutPage(Model model) {


        return "about";
    }
}
