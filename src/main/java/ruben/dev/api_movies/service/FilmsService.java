package ruben.dev.api_movies.service;

import java.util.List;

import ruben.dev.api_movies.dtos.FilmsRequestDTO;
import ruben.dev.api_movies.dtos.FilmsResponseDTO;

public interface FilmsService {

    List<FilmsResponseDTO> findAll();

    FilmsResponseDTO findById(Long id);

    FilmsResponseDTO save(FilmsRequestDTO request);

    FilmsResponseDTO update(Long id, FilmsRequestDTO request);

    List<FilmsResponseDTO> searchByTitleOrGenre(String title, String genre);

    void deleteById(Long id);
}
