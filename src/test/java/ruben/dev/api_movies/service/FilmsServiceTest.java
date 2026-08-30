package ruben.dev.api_movies.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        ruben.dev.api_movies.dtos.FilmsRequestDTO request = new ruben.dev.api_movies.dtos.FilmsRequestDTO();
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
}
