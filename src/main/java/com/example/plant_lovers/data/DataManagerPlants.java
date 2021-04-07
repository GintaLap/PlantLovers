package com.example.plant_lovers.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;

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

    public Plant getPlantById(int plantId) {
        var session = factory.openSession();

        try {
            String hql = "FROM Plant P WHERE P.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", plantId);
            var results = query.list();

            if (results.size() > 0) {
                return (Plant) results.get(0);
            }
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return null;
    }



    public Plant getPlantByName(String name) {
        var session = factory.openSession();

        try {
            String hql = "FROM Plant P WHERE P.name = :pName";
            Query query = session.createQuery(hql);
            query.setParameter("pName", name);
            var results = query.list();

            if (results.size() > 0) {
                return (Plant) results.get(0);
            }
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return null;
    }


}
