package com.github.serezhka.srzhkabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 * @since 19.05.2018
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(App.class, args);
    }
}
