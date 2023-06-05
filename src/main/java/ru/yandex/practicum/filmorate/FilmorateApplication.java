package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.GsonBuilder;

import java.time.LocalDate;

@SpringBootApplication
public class FilmorateApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(FilmorateApplication.class);
	}
}