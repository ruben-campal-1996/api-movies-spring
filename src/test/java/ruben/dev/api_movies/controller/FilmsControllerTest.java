package ruben.dev.api_movies.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
