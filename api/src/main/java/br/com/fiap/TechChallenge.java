package br.com.fiap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechChallenge {

    public static void main(String[] args) {
        SpringApplication.run(TechChallenge.class, args);

        System.out.println("HELLO WORLD");
    }
}