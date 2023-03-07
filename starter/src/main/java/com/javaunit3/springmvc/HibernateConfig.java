package com.javaunit3.springmvc;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.javaunit3.springmvc.model.MovieEntity;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory getSessionFactory() {
        SessionFactory factory = new org.hibernate.cfg.Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(MovieEntity.class)
            .buildSessionFactory();
        
        return factory;
    }


}
