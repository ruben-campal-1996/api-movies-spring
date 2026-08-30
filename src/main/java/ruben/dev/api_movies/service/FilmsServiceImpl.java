package ruben.dev.api_movies.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public FilmsResponseDTO update(Long id, FilmsRequestDTO request) {
        FilmsEntity film = filmsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con id: " + id));

        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setYear(new YearsEntity(film.getYear() != null ? film.getYear().getId_year() : null, request.getReleaseYear()));

        FilmsEntity updatedFilm = filmsRepository.save(film);
        return filmsMapper.toResponseDto(updatedFilm);
    }

    @Override
    public List<FilmsResponseDTO> searchByTitleOrGenre(String title, String genre) {
        List<FilmsEntity> results = new ArrayList<>();
        Map<Long, FilmsEntity> uniqueResults = new LinkedHashMap<>();

        if (title != null && !title.isBlank()) {
            results.addAll(filmsRepository.findByNameContainingIgnoreCase(title.trim()));
        }

        if (genre != null && !genre.isBlank()) {
            results.addAll(filmsRepository.findByGenres_NameContainingIgnoreCase(genre.trim()));
        }

        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron películas con ese criterio");
        }

        for (FilmsEntity film : results) {
            uniqueResults.putIfAbsent(film.getId(), film);
        }

        List<FilmsResponseDTO> response = uniqueResults.values().stream()
            .map(filmsMapper::toResponseDto)
            .toList();

        if (response.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron películas con ese criterio");
        }

        return response;
    }
}
