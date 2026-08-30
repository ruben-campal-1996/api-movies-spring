package ruben.dev.api_movies.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ruben.dev.api_movies.dtos.FilmsResponseDTO;
import ruben.dev.api_movies.exception.GlobalExceptionHandler;
import ruben.dev.api_movies.exception.ResourceNotFoundException;
import ruben.dev.api_movies.service.FilmsService;

@ExtendWith(MockitoExtension.class)
class FilmsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FilmsService filmsService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FilmsController(filmsService))
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void findAll_shouldReturnHttp200_whenMoviesExist() throws Exception {
        FilmsResponseDTO response = new FilmsResponseDTO(1L, "Matrix", "Ciencia ficción", 1999);
        given(filmsService.findAll()).willReturn(List.of(response));

        mockMvc.perform(get("/api/v1/movies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Matrix"))
            .andExpect(jsonPath("$[0].releaseYear").value(1999));
    }

    @Test
    void findAll_shouldReturnHttp404_whenNoMoviesAreFound() throws Exception {
        given(filmsService.findAll()).willThrow(new ResourceNotFoundException("No se encontraron películas"));

        mockMvc.perform(get("/api/v1/movies"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("No se encontraron películas"));
    }

    @Test
    void findById_shouldReturnHttp200_whenMovieExists() throws Exception {
        FilmsResponseDTO response = new FilmsResponseDTO(1L, "Matrix", "Ciencia ficción", 1999);
        given(filmsService.findById(1L)).willReturn(response);

        mockMvc.perform(get("/api/v1/movies/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Matrix"))
            .andExpect(jsonPath("$.releaseYear").value(1999));
    }

    @Test
    void findById_shouldReturnHttp404_whenMovieDoesNotExist() throws Exception {
        given(filmsService.findById(99L)).willThrow(new ResourceNotFoundException("Película no encontrada con id: 99"));

        mockMvc.perform(get("/api/v1/movies/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Película no encontrada con id: 99"));
    }

    @Test
    void save_shouldReturnHttp201_whenRequestIsValid() throws Exception {
        FilmsResponseDTO response = new FilmsResponseDTO(2L, "Inception", "Sueños", 2010);
        given(filmsService.save(org.mockito.ArgumentMatchers.any())).willReturn(response);

        mockMvc.perform(post("/api/v1/movies")
                .contentType("application/json")
                .content("{\"name\":\"Inception\",\"description\":\"Sueños\",\"releaseYear\":2010}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Inception"))
            .andExpect(jsonPath("$.releaseYear").value(2010));
    }

    @Test
    void save_shouldReturnHttp400_whenRequestIsInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/movies")
                .contentType("application/json")
                .content("{\"name\":\"\",\"description\":\"\",\"releaseYear\":null}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnHttp200_whenRequestIsValid() throws Exception {
        FilmsResponseDTO response = new FilmsResponseDTO(1L, "Inception", "Sueños", 2010);
        given(filmsService.update(org.mockito.ArgumentMatchers.eq(1L), org.mockito.ArgumentMatchers.any())).willReturn(response);

        mockMvc.perform(put("/api/v1/movies/1")
                .contentType("application/json")
                .content("{\"name\":\"Inception\",\"description\":\"Sueños\",\"releaseYear\":2010}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Inception"))
            .andExpect(jsonPath("$.releaseYear").value(2010));
    }

    @Test
    void update_shouldReturnHttp404_whenMovieDoesNotExist() throws Exception {
        given(filmsService.update(org.mockito.ArgumentMatchers.eq(99L), org.mockito.ArgumentMatchers.any()))
            .willThrow(new ResourceNotFoundException("Película no encontrada con id: 99"));

        mockMvc.perform(put("/api/v1/movies/99")
                .contentType("application/json")
                .content("{\"name\":\"Inception\",\"description\":\"Sueños\",\"releaseYear\":2010}"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Película no encontrada con id: 99"));
    }
}
