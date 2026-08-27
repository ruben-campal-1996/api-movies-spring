package ruben.dev.api_movies.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication(scanBasePackages = "ruben.dev.api_movies")
@EnableJpaRepositories(basePackages = "ruben.dev.api_movies.repository")
@EntityScan(basePackages = "ruben.dev.api_movies.entity")
public class AppContainer {

	public static void main(String[] args) {
		SpringApplication.run(AppContainer.class, args);
	}

}
