package com.laurynas.kacinauskas.revolut.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

class SessionUtil {

    private SessionUtil() {
    }

    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSession() {
        return sessionFactory;
    }

    static {
        try {
            standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);

            Metadata metadata = metadataSources.getMetadataBuilder().build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            // TODO: add logging
            if (standardServiceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
            }
        }
    }

}
