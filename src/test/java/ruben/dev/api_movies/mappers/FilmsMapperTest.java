package ruben.dev.api_movies.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import ruben.dev.api_movies.dtos.FilmsResponseDTO;
import ruben.dev.api_movies.entity.FilmsEntity;
import ruben.dev.api_movies.entity.YearsEntity;

class FilmsMapperTest {

    private final FilmsMapper filmsMapper = new FilmsMapper();

    @Test
    void toResponseDto_shouldReturnNull_whenFilmIsNull() {
        assertThat(filmsMapper.toResponseDto(null)).isNull();
    }

    @Test
    void toResponseDto_shouldMapFilmWithYear() {
        FilmsEntity film = new FilmsEntity(1L, "Matrix", "Ciencia ficción", new YearsEntity(10L, 1999));

        FilmsResponseDTO result = filmsMapper.toResponseDto(film);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Matrix");
        assertThat(result.getDescription()).isEqualTo("Ciencia ficción");
        assertThat(result.getReleaseYear()).isEqualTo(1999);
    }

    @Test
    void toResponseDto_shouldMapYearAsNull_whenFilmHasNoYear() {
        FilmsEntity film = new FilmsEntity(2L, "Alien", "Naves y monstruos", null);

        FilmsResponseDTO result = filmsMapper.toResponseDto(film);

        assertThat(result.getReleaseYear()).isNull();
    }

    @Test
    void toResponseDtoList_shouldMapAllFilms() {
        FilmsEntity film1 = new FilmsEntity(1L, "Matrix", "Ciencia ficción", new YearsEntity(10L, 1999));
        FilmsEntity film2 = new FilmsEntity(2L, "Alien", "Monstruo", new YearsEntity(11L, 1979));

        List<FilmsResponseDTO> result = filmsMapper.toResponseDtoList(List.of(film1, film2));

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Matrix");
        assertThat(result.get(1).getName()).isEqualTo("Alien");
        assertThat(result.get(1).getReleaseYear()).isEqualTo(1979);
    }
}
