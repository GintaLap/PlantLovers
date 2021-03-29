package com.example.plant_lovers.plant_lovers;

import com.example.plant_lovers.data.DataManagerPlants;
import com.example.plant_lovers.data.Plant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PlantController {

    @GetMapping("/all_plants")
    public String getAllPlantsPage(Model model) {

        var dm = new DataManagerPlants();
        var plants = dm.getPlants();

        model.addAttribute("plants", plants);

        return "all_plants";
    }

    @GetMapping("/about")
    public String getAboutPage(Model model) {


        return "about";
    }
}
