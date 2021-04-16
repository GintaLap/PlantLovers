package com.example.plant_lovers;

import com.example.plant_lovers.data.DataManagerGarden;
import com.example.plant_lovers.data.DataManagerPlants;
import com.example.plant_lovers.models.WateringModel;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "CalendarJasonServlet", value = "/CalendarJasonServlet")
public class CalendarJasonServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response,int id) throws ServletException, IOException {

        var dmg = new DataManagerGarden();
        var myGarden = dmg.getGarden().stream().
                filter(g -> (g.getUserId().equals(id)))
                .collect(Collectors.toList());


        var wateringDate = myGarden.stream()
                .map(p -> new WateringModel(p.getPlant().getName(), p.getWaterDate())).collect(Collectors.toList());


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(new Gson().toJson(wateringDate));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
