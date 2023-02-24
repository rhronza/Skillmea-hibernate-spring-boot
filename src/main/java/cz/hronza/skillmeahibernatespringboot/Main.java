package cz.hronza.skillmeahibernatespringboot;

import cz.hronza.skillmeahibernatespringboot.entity.PersonEntity;
import cz.hronza.skillmeahibernatespringboot.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Main.class, args);
        List<PersonEntity> all = configurableApplicationContext.getBean(PersonService.class).findAll();

        log.info("");
        all.forEach(person -> {
            String message = person.toString();
            log.info(message);
        });
        log.info("");
    }

}
