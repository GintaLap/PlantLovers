package com.example.plant_lovers.data;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DataManagerUser {
    private static SessionFactory factory;


    public DataManagerUser() {
        try {
            factory = new Configuration()
                    .configure()
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void addUser(Object item) { //original
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

    public List<User> getUsers() {
        var session = factory.openSession();
        try {
            return session.createQuery("FROM User").list();
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public User login(String email) {
        var session = factory.openSession();


        try {
            String hql = "FROM User U WHERE U.email = :uEmail";

            Query query = session.createQuery(hql);

            query.setParameter("uEmail", email);

            var results = query.list();


            if (results.size() > 0) {
                return (User) results.get(0);
            }
        } catch (HibernateException ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }

        return null;
    }

}


