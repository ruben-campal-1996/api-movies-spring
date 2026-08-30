package ruben.dev.api_movies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ruben.dev.api_movies.dtos.FilmsResponseDTO;
import ruben.dev.api_movies.exception.ResourceNotFoundException;
import ruben.dev.api_movies.mappers.FilmsMapper;
import ruben.dev.api_movies.repository.FilmsRepository;

@Service
public class FilmsServiceImpl implements FilmsService {
    private final FilmsRepository filmsRepository;
    private final FilmsMapper filmsMapper;

    public FilmsServiceImpl(FilmsRepository filmsRepository, FilmsMapper filmsMapper) {
        this.filmsRepository = filmsRepository;
        this.filmsMapper = filmsMapper;
    }

    @Override
    public List<FilmsResponseDTO> findAll() {
        List<FilmsResponseDTO> films = filmsMapper.toResponseDtoList(filmsRepository.findAll());
        if (films.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron películas");
        }
        return films;
    }

}
