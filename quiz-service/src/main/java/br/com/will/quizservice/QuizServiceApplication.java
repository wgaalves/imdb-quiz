package br.com.will.quizservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableFeignClients
@SpringBootApplication
public class QuizServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizServiceApplication.class, args);
    }

}
