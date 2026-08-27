package ruben.dev.api_movies;

import org.springframework.boot.SpringApplication;
import ruben.dev.api_movies.config.AppContainer;

public class TestApiMoviesApplication {

	public static void main(String[] args) {
		SpringApplication.from(AppContainer::main).with(TestcontainersConfiguration.class).run(args);
	}

}
