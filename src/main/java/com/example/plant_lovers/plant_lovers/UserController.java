package com.example.plant_lovers.plant_lovers;

import com.example.plant_lovers.data.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {


    //needs to do some fixing, not working at the moment
    @PostMapping("/login")
    public ModelAndView addUser(@ModelAttribute("addUserData") UserDTO dto) {
        var dm = new DataManagerUser();

        var user = new User(0, dto.getULogin(), dto.getUName(), dto.getUPassword());

        dm.addUser(user);

        return new ModelAndView("redirect:/");
    }


        @GetMapping("/your_garden")
    public ModelAndView addGarden(@ModelAttribute("addGardenData") UserDTO dto) {

        var dm = new DataManagerGarden();



        return null;
    }
}
