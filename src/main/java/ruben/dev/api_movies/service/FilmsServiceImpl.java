package ruben.dev.api_movies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ruben.dev.api_movies.dtos.FilmsRequestDTO;
import ruben.dev.api_movies.dtos.FilmsResponseDTO;
import ruben.dev.api_movies.entity.FilmsEntity;
import ruben.dev.api_movies.entity.YearsEntity;
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

    @Override
    public FilmsResponseDTO findById(Long id) {
        return filmsRepository.findById(id)
            .map(filmsMapper::toResponseDto)
            .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con id: " + id));
    }

    @Override
    public FilmsResponseDTO save(FilmsRequestDTO request) {
        FilmsEntity film = new FilmsEntity();
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setYear(new YearsEntity(null, request.getReleaseYear()));

        FilmsEntity savedFilm = filmsRepository.save(film);
        return filmsMapper.toResponseDto(savedFilm);
    }
}
