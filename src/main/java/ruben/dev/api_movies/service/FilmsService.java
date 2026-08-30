package ruben.dev.api_movies.service;

import java.util.List;

import ruben.dev.api_movies.dtos.FilmsResponseDTO;

public interface FilmsService {

    List<FilmsResponseDTO> findAll();
}
