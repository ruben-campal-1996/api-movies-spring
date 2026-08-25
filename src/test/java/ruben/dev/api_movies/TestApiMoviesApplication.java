package ruben.dev.api_movies;

import org.springframework.boot.SpringApplication;

public class TestApiMoviesApplication {

	public static void main(String[] args) {
		SpringApplication.from(ApiMoviesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
