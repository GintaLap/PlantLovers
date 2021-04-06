package com.example.plant_lovers.data;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class DataManagerPlants {
    private static SessionFactory factory;

    public DataManagerPlants() {
        try {
            factory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Plant.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public List<Plant> getPlants() {
        var session = factory.openSession();
        try {
            return session.createQuery("FROM Plant").list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }
}
