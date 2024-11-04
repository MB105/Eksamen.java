package config;

import config.HibernateConfig;
import daos.impl.TripDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populator {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        TripDAO tripDAO = TripDAO.getInstance(emf);


        tripDAO.populateDatabase();

        System.out.println("Database populated successfully.");




    }
}

