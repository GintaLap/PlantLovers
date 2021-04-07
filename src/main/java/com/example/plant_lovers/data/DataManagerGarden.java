package com.example.plant_lovers.data;

import lombok.NonNull;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DataManagerGarden {
    private static SessionFactory factory;

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

    public void addGarden(Object item) {
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

    public List<Plant> getYourPlants(int id){
        var myGarden = getGarden().stream().
                filter(g-> (g.getUserId().equals(id))).collect(Collectors.toList());

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

    public void save(@NonNull Object item) {
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(item);
            tx.commit();
        } catch (HibernateException exception) {
            if(tx != null) {
                tx.rollback();
            }
            System.err.println(exception);
        } finally {
            session.close();
        }
    }

    public void addCar(@NonNull Garden garden) {
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(garden);
            tx.commit();
        } catch (HibernateException exception) {
            if(tx != null) {
                tx.rollback();
            }
            System.err.println(exception);
        } finally {
            session.close();
        }
    }
}
