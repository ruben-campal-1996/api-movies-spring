package ruben.dev.api_movies.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ruben.dev.api_movies.dtos.FilmsRequestDTO;
import ruben.dev.api_movies.dtos.FilmsResponseDTO;
import ruben.dev.api_movies.entity.FilmsEntity;
import ruben.dev.api_movies.entity.YearsEntity;
import ruben.dev.api_movies.exception.ResourceNotFoundException;
import ruben.dev.api_movies.mappers.FilmsMapper;
import ruben.dev.api_movies.repository.FilmsRepository;

@ExtendWith(MockitoExtension.class)
class FilmsServiceTest {

    @Mock
    private FilmsRepository filmsRepository;

    @Mock
    private FilmsMapper filmsMapper;

    @InjectMocks
    private FilmsServiceImpl filmsService;

    @Test
    void findAll_shouldReturnDtoList_whenRepositoryHasData() {
        FilmsEntity film = new FilmsEntity(1L, "Matrix", "Ciencia ficción", new YearsEntity(1L, 1999));
        FilmsResponseDTO expected = new FilmsResponseDTO(1L, "Matrix", "Ciencia ficción", 1999);

        when(filmsRepository.findAll()).thenReturn(List.of(film));
        when(filmsMapper.toResponseDtoList(List.of(film))).thenReturn(List.of(expected));

        List<FilmsResponseDTO> result = filmsService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Matrix");
        assertThat(result.getFirst().getReleaseYear()).isEqualTo(1999);
    }

    @Test
    void findAll_shouldThrowResourceNotFoundException_whenRepositoryIsEmpty() {
        when(filmsRepository.findAll()).thenReturn(List.of());
        when(filmsMapper.toResponseDtoList(List.of())).thenReturn(List.of());

        assertThatThrownBy(() -> filmsService.findAll())
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("No se encontraron películas");
    }

    @Test
    void findById_shouldReturnFilm_whenIdExists() {
        FilmsEntity film = new FilmsEntity(1L, "Matrix", "Ciencia ficción", new YearsEntity(1L, 1999));
        FilmsResponseDTO expected = new FilmsResponseDTO(1L, "Matrix", "Ciencia ficción", 1999);

        when(filmsRepository.findById(1L)).thenReturn(java.util.Optional.of(film));
        when(filmsMapper.toResponseDto(film)).thenReturn(expected);

        FilmsResponseDTO result = filmsService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Matrix");
        assertThat(result.getReleaseYear()).isEqualTo(1999);
    }

    @Test
    void findById_shouldThrowResourceNotFoundException_whenIdDoesNotExist() {
        when(filmsRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> filmsService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Película no encontrada con id: 99");
    }

    @Test
    void save_shouldReturnCreatedMovie_whenRequestIsValid() {
        FilmsRequestDTO request = new FilmsRequestDTO();
        request.setName("Inception");
        request.setDescription("Sueños");
        request.setReleaseYear(2010);

        FilmsEntity savedFilm = new FilmsEntity(2L, "Inception", "Sueños", new YearsEntity(2L, 2010));
        FilmsResponseDTO expected = new FilmsResponseDTO(2L, "Inception", "Sueños", 2010);

        when(filmsRepository.save(org.mockito.ArgumentMatchers.any(FilmsEntity.class))).thenReturn(savedFilm);
        when(filmsMapper.toResponseDto(savedFilm)).thenReturn(expected);

        FilmsResponseDTO result = filmsService.save(request);

        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Inception");
        assertThat(result.getReleaseYear()).isEqualTo(2010);
    }

    @Test
    void update_shouldReturnUpdatedMovie_whenMovieExists() {
        FilmsRequestDTO request = new FilmsRequestDTO("Inception", "Sueños", 2010);
        FilmsEntity existingFilm = new FilmsEntity(1L, "Matrix", "Ciencia ficción", new YearsEntity(1L, 1999));
        FilmsEntity updatedFilm = new FilmsEntity(1L, "Inception", "Sueños", new YearsEntity(1L, 2010));
        FilmsResponseDTO expected = new FilmsResponseDTO(1L, "Inception", "Sueños", 2010);

        when(filmsRepository.findById(1L)).thenReturn(Optional.of(existingFilm));
        when(filmsRepository.save(existingFilm)).thenReturn(updatedFilm);
        when(filmsMapper.toResponseDto(updatedFilm)).thenReturn(expected);

        FilmsResponseDTO result = filmsService.update(1L, request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Inception");
        assertThat(result.getReleaseYear()).isEqualTo(2010);
    }

    @Test
    void update_shouldThrowResourceNotFoundException_whenMovieDoesNotExist() {
        FilmsRequestDTO request = new FilmsRequestDTO("Inception", "Sueños", 2010);

        when(filmsRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> filmsService.update(99L, request))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Película no encontrada con id: 99");
    }

    @Test
    void searchByTitleOrGenre_shouldReturnMovies_whenTitleMatches() {
        FilmsEntity film = new FilmsEntity(1L, "Matrix", "Ciencia ficción", new YearsEntity(1L, 1999));
        FilmsResponseDTO expected = new FilmsResponseDTO(1L, "Matrix", "Ciencia ficción", 1999);

        when(filmsRepository.findByNameContainingIgnoreCase("Matrix")).thenReturn(List.of(film));
        when(filmsMapper.toResponseDto(film)).thenReturn(expected);

        List<FilmsResponseDTO> result = filmsService.searchByTitleOrGenre("Matrix", null);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Matrix");
    }

    @Test
    void searchByTitleOrGenre_shouldReturnMovies_whenGenreMatches() {
        FilmsEntity film = new FilmsEntity(1L, "Matrix", "Ciencia ficción", new YearsEntity(1L, 1999));
        FilmsResponseDTO expected = new FilmsResponseDTO(1L, "Matrix", "Ciencia ficción", 1999);

        when(filmsRepository.findByGenres_NameContainingIgnoreCase("Ciencia")).thenReturn(List.of(film));
        when(filmsMapper.toResponseDto(film)).thenReturn(expected);

        List<FilmsResponseDTO> result = filmsService.searchByTitleOrGenre(null, "Ciencia");

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Matrix");
    }

    @Test
    void searchByTitleOrGenre_shouldThrowResourceNotFoundException_whenNoMoviesMatch() {
        when(filmsRepository.findByNameContainingIgnoreCase("NoExiste")).thenReturn(List.of());
        when(filmsRepository.findByGenres_NameContainingIgnoreCase("NoExiste")).thenReturn(List.of());

        assertThatThrownBy(() -> filmsService.searchByTitleOrGenre("NoExiste", "NoExiste"))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("No se encontraron películas con ese criterio");
    }
}
