package com.example.plant_lovers.controllers;


import com.example.plant_lovers.SesstionData.SessionData;
import com.example.plant_lovers.data.*;
import com.example.plant_lovers.dto.GardenDTO;
import com.example.plant_lovers.models.AddGardenModel;
import com.example.plant_lovers.models.WateringModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/")
public class GardenController {
    private DataManagerUser dm;
    private DataManagerGarden dmg;
    private DataManagerPlants dmp;

    public GardenController() {
        dm = new DataManagerUser();
        dmg = new DataManagerGarden();
        dmp = new DataManagerPlants();
    }



}
