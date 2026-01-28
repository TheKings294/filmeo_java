package com.filmeo.webapp.config;

import com.filmeo.webapp.utils.RefillDb;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbLoader {

    @Bean
    public CommandLineRunner initData(RefillDb refillDb) {
        return args -> {
            System.out.println("--- Début de la génération des données ---");
            refillDb.init();
            System.out.println("--- Données générées avec succès ! ---");
        };
    }
}
