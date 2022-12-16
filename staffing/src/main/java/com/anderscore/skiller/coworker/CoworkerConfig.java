package com.anderscore.skiller.coworker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CoworkerConfig {
    @Bean
    CommandLineRunner commandLineRunner(CoworkerRepository repository) {
        return args -> {
            Coworker kilian = new Coworker("Kilian", "Peters");
            Coworker miriam = new Coworker("Miriam", "House");
//            repository.saveAll(
//                    List.of(kilian, miriam)
//            );
        };

    }
}
