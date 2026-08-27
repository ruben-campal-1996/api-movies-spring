package ruben.dev.api_movies;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import ruben.dev.api_movies.config.AppContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(classes = AppContainer.class)
class ApiMoviesApplicationTests {

	@Test
	void contextLoads() {
	}

}
