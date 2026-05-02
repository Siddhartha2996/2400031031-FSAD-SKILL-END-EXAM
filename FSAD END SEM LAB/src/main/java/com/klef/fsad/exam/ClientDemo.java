package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ClientDemo {

    public static void main(String[] args) {
    
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            
            System.out.println("--- I. Inserting Records ---");
            insertRecord(sessionFactory);

            System.out.println("\n--- II. Viewing All Records ---");
            viewAllRecords(sessionFactory);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertRecord(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Transport t1 = new Transport();
            t1.setId(1);
            t1.setName("Express Bus");
            t1.setDate("2023-11-01");
            t1.setStatus("Active");
            t1.setType("Road");
            t1.setCost(150.0);

            Transport t2 = new Transport();
            t2.setId(2);
            t2.setName("Cargo Train");
            t2.setDate("2023-11-02");
            t2.setStatus("Active");
            t2.setType("Rail");
            t2.setCost(5000.0);
            
            Transport t3 = new Transport();
            t3.setId(3);
            t3.setName("Local Flight");
            t3.setDate("2023-11-03");
            t3.setStatus("Maintenance");
            t3.setType("Air");
            t3.setCost(12000.0);

            session.persist(t1);
            session.persist(t2);
            session.persist(t3);

            transaction.commit();
            System.out.println("Records inserted successfully.");
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void viewAllRecords(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();

        try {
            
            String hql = "from Transport";
            Query<Transport> query = session.createQuery(hql, Transport.class);
            
            List<Transport> records = query.list();
            
            for (Transport t : records) {
                System.out.println(t.toString());
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
