package com.javaconcepts.doc;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaConceptsDocApplication {

    public static void main(String[] args) {
        loadDotenv();
        SpringApplication.run(JavaConceptsDocApplication.class, args);
    }

    private static void loadDotenv() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
            dotenv.entries().forEach(entry ->
                    System.setProperty(entry.getKey(), entry.getValue())
            );
        } catch (Exception e) {
            System.err.println("No se pudo cargar .env: " + e.getMessage());
        }
    }
}
