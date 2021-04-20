package com.example.plant_lovers.data;


import com.example.plant_lovers.models.WateringEventModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DataManagerGarden {
    private static SessionFactory factory;
    private static final String FILE_PATH = "src/main/resources/calendar_data.json";

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


    public List<LocalDateTime> gettingWateringDates(int id) {

        var repeatTimes = 55;

        var myGarden = getGarden().stream().
                filter(g -> (g.getUserId().equals(id)))
                .collect(Collectors.toList());

        var startDate = myGarden.stream()
                .map(Garden::getWaterDate).collect(Collectors.toList());

        var plantN = myGarden.stream().map(p -> (p.getPlant().getName())).findFirst().toString();

        var dayCount = myGarden.stream()
                .map(w -> (w.getPlant().getWatering())
                ).collect(Collectors.toList());

        List<LocalDateTime> repDate = new ArrayList<>();

        for (int i = 0; i < startDate.size(); i++) {
            var stDate = startDate.get(i);
            var day = dayCount.get(i);

            for (int k = 0; k < repeatTimes; k++) { //repeatTimes
                repDate.add(stDate);
                stDate = stDate.plusDays(day);

            }
        }
        return repDate;
    }

    public List<WateringEventModel> getRepCalendarDataForJson(int id) {
        var repeatTimes = 20;
        List<WateringEventModel> wateringDate = new ArrayList<>();

        for (int i = 0; i < repeatTimes; i++) {
            var myGarden = getGarden().stream().
                    filter(g -> (g.getUserId().equals(id)))
                    .collect(Collectors.toList());

            var allDay = "true";

            var start =  myGarden.get(id).getWaterDate().plusDays(myGarden.get(id).getPlant().getWatering()).toString();
            var waterM = new WateringEventModel(myGarden.get(id).getPlant().getName(),start, allDay);
            if(start == start){
                start=myGarden.get(id).getWaterDate().plusDays(myGarden.get(id).getPlant().getWatering()).toString();
            }


            wateringDate.add(waterM);
        }




        return wateringDate;

    }


    public List<WateringEventModel> getCalendarDataForJson(int id) {

        var myGarden = getGarden().stream().
                filter(g -> (g.getUserId().equals(id)))
                .collect(Collectors.toList());
        var allDay = "true";

        var wateringDate = myGarden.stream()
                .map(p -> new WateringEventModel(p.getPlant().getName(), p.getWaterDate().toString()
                        , allDay)).collect(Collectors.toList());

        return wateringDate;

    }

    public void saveToJason(int id) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var wateringDates = getCalendarDataForJson(id);

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_PATH), wateringDates);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<WateringEventModel> load() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(FILE_PATH), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
