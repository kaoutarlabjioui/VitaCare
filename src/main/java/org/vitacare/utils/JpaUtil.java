package org.vitacare.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JpaUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("VitaCarePU");

    @Produces
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

