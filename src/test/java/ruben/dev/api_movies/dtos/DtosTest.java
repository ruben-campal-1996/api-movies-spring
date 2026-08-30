package ruben.dev.api_movies.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class DtosTest {

    @Test
    void apiErrorDto_shouldSetAndGetValues() {
        Instant now = Instant.now();
        ApiErrorDTO dto = new ApiErrorDTO();

        dto.setCode("404");
        dto.setMessage("Not found");
        dto.setTimestamp(now);

        assertThat(dto.getCode()).isEqualTo("404");
        assertThat(dto.getMessage()).isEqualTo("Not found");
        assertThat(dto.getTimestamp()).isEqualTo(now);

        ApiErrorDTO second = new ApiErrorDTO("500", "Error", now);
        assertThat(second.getCode()).isEqualTo("500");
        assertThat(second.getMessage()).isEqualTo("Error");
        assertThat(second.getTimestamp()).isEqualTo(now);
    }

    @Test
    void filmsResponseDto_shouldSetAndGetValues() {
        FilmsResponseDTO dto = new FilmsResponseDTO();

        dto.setId(7L);
        dto.setName("Alien");
        dto.setDescription("Monstruo");
        dto.setReleaseYear(1979);

        assertThat(dto.getId()).isEqualTo(7L);
        assertThat(dto.getName()).isEqualTo("Alien");
        assertThat(dto.getDescription()).isEqualTo("Monstruo");
        assertThat(dto.getReleaseYear()).isEqualTo(1979);

        FilmsResponseDTO second = new FilmsResponseDTO(8L, "Matrix", "Ciencia", 1999);
        assertThat(second.getId()).isEqualTo(8L);
        assertThat(second.getName()).isEqualTo("Matrix");
        assertThat(second.getDescription()).isEqualTo("Ciencia");
        assertThat(second.getReleaseYear()).isEqualTo(1999);
    }
}
