package com.example.plant_lovers.data;


import com.example.plant_lovers.models.WateringModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import lombok.NonNull;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class DataManagerGarden {
    private static SessionFactory factory;
    private static final String FILE_PATH = "src/main/resources/templates/calendar_data.json";

    public DataManagerGarden() {
        try {
            factory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Garden.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Plant.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void addGarden(@NonNull Object item) {
        var session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(item);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex);
        } finally {
            session.close();
        }
    }

    public List<Garden> getGarden() {

        var session = factory.openSession();
        try {
            return session.createQuery("FROM Garden ").list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public List<Plant> getYourPlants(int id) {
        var myGarden = getGarden().stream().
                filter(g -> (g.getUserId().equals(id))).collect(Collectors.toList());

        var myPlants = myGarden.stream()
                .map(p -> new Plant(
                        p.getPlant().getId(),
                        p.getPlant().getImageId(),
                        p.getPlant().getScienceName(),
                        p.getPlant().getName(),
                        p.getPlant().getDescription(),
                        p.getPlant().getMoisture(),
                        p.getPlant().getSunlight(),
                        p.getPlant().getWatering(),
                        p.getPlant().getPetToxic(),
                        p.getPlant().getType(),
                        p.getPlant().getBloom(),
                        p.getPlant().getHumidity()))
                .collect(Collectors.toList());

        return myPlants;
    }


    public void deleteGarden(int id) {
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            var garden = session.get(Garden.class, id);
            if (garden != null) {
                session.delete(garden);
            }
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(exception);
        } finally {
            session.close();
        }
    }

    public List<WateringModel> getDateAndPlant(int id) {
        var dmp = new DataManagerPlants();
        var myGarden = getGarden().stream().
                filter(g -> (g.getUserId().equals(id)))
                .collect(Collectors.toList());


        var wateringDate = myGarden.stream()
                .map(p -> new WateringModel(p.getPlant().getName(), p.getWaterDate())).collect(Collectors.toList());

        return wateringDate;
    }


    //    public List<WateringModel> getDateAndPlant(int id) {
//        var dmp = new DataManagerPlants();
//
//        var repeatTimes = 55;
//
//        var myGarden = getGarden().stream().
//                filter(g -> (g.getUserId().equals(id)))
//                .collect(Collectors.toList());
//
//
//        var plantN = myGarden.stream().map(p -> (p.getPlant().getName())).findFirst().toString();
//
//        var startDate = myGarden.stream().map(Garden::getWaterDate).findFirst();
//
//
//        var dayCount = myGarden.stream()
//                .filter(dp -> (dp.getPlant().getName().equals(plantN)))
//                .map(garden -> dmp.getPlantById(myGarden.get(id)
//                        .getPlant().getId()).getWatering()).findFirst();
//
//
//        for (int i = 0; i < repeatTimes; i++) {
//
//        }
//
//
//        var wateringDate = new WateringModel(plantN, startDate, repDate);
//
//        return wateringDate;
//    }
//
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void saveToJason(List<WateringModel> wateringDates) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_PATH), wateringDates);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void gettingWateringDate(int id) {
        var dmp = new DataManagerPlants();
        var myGarden = getGarden().stream().
                filter(g -> (g.getUserId().equals(id)))
                .collect(Collectors.toList());

        var startDate = myGarden.stream().map(Garden::getWaterDate).collect(Collectors.toList());

        var wateringInDataBase = myGarden.stream().
                map(w -> (w.getPlant().getWatering())
                ).collect(Collectors.toList());

        for (int i = 0; i < startDate.size(); i++) {
            var wateringPlan = startDate.get(i).plusDays(wateringInDataBase.get(i));
           System.out.println(wateringPlan + " ");
        }
    }

    @Test
    public void getting_watering_date() {
        gettingWateringDate((83));
    }


//    public void doGet(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
//
//
//        var dmp = new DataManagerPlants();
//        var myGarden = getGarden().stream().
//                filter(g -> (g.getUserId().equals(id)))
//                .collect(Collectors.toList());
//
//
//        var wateringDate = myGarden.stream()
//                .map(p -> new WateringModel(p.getPlant().getName(), p.getWaterDate())).collect(Collectors.toList());
//
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        out.write(new Gson().toJson(wateringDate));
//    }
    }
