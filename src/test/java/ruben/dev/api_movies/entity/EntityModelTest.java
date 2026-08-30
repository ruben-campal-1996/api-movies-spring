package ruben.dev.api_movies.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

class EntityModelTest {

    @Test
    void actorsEntity_shouldStoreValues() {
        ActorsEntity entity = new ActorsEntity(1L, "Sigourney Weaver");

        assertThat(entity.getId_actor()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("Sigourney Weaver");
    }

    @Test
    void genreEntity_shouldStoreValues() {
        GenreEntity entity = new GenreEntity(2L, "Ciencia ficcion");

        assertThat(entity.getId_genre()).isEqualTo(2L);
        assertThat(entity.getName()).isEqualTo("Ciencia ficcion");
    }

    @Test
    void yearsEntity_shouldStoreValues() {
        YearsEntity entity = new YearsEntity(3L, 1981);

        assertThat(entity.getId_year()).isEqualTo(3L);
        assertThat(entity.getYear()).isEqualTo(1981);
        assertThat(entity.getFilms()).isNotNull();
    }

    @Test
    void filmsEntity_shouldStoreRelationsAndValues() {
        YearsEntity year = new YearsEntity(4L, 1999);
        FilmsEntity entity = new FilmsEntity(5L, "Matrix", "Ciencia ficción", year);

        entity.setName("Alien");
        entity.setDescription("Monstruo");
        entity.setYear(year);

        assertThat(entity.getId()).isEqualTo(5L);
        assertThat(entity.getName()).isEqualTo("Alien");
        assertThat(entity.getDescription()).isEqualTo("Monstruo");
        assertThat(entity.getYear()).isEqualTo(year);
        assertThat(entity.getGenres()).isInstanceOf(Set.class);
        assertThat(entity.getActors()).isInstanceOf(Set.class);
    }
}
