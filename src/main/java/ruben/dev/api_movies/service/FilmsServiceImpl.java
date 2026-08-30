package ruben.dev.api_movies.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruben.dev.api_movies.dtos.FilmsRequestDTO;
import ruben.dev.api_movies.dtos.FilmsResponseDTO;
import ruben.dev.api_movies.entity.FilmsEntity;
import ruben.dev.api_movies.entity.YearsEntity;
import ruben.dev.api_movies.exception.ResourceNotFoundException;
import ruben.dev.api_movies.mappers.FilmsMapper;
import ruben.dev.api_movies.repository.FilmsRepository;
import ruben.dev.api_movies.repository.YearsRepository;

@Service
public class FilmsServiceImpl implements FilmsService {
    private final FilmsRepository filmsRepository;
    private final FilmsMapper filmsMapper;
    private final YearsRepository yearsRepository;

    public FilmsServiceImpl(FilmsRepository filmsRepository, FilmsMapper filmsMapper, YearsRepository yearsRepository) {
        this.filmsRepository = filmsRepository;
        this.filmsMapper = filmsMapper;
        this.yearsRepository = yearsRepository;
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
    @Transactional
    public FilmsResponseDTO save(FilmsRequestDTO request) {
        YearsEntity year = yearsRepository.findByYear(request.getReleaseYear())
            .orElseGet(() -> yearsRepository.save(new YearsEntity(null, request.getReleaseYear())));

        FilmsEntity film = new FilmsEntity();
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setYear(year);

        FilmsEntity savedFilm = filmsRepository.save(film);
        return filmsMapper.toResponseDto(savedFilm);
    }

    @Override
    @Transactional
    public FilmsResponseDTO update(Long id, FilmsRequestDTO request) {
        FilmsEntity film = filmsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con id: " + id));

        YearsEntity year = yearsRepository.findByYear(request.getReleaseYear())
            .orElseGet(() -> yearsRepository.save(new YearsEntity(null, request.getReleaseYear())));

        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setYear(year);

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

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!filmsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Película no encontrada con id: " + id);
        }
        filmsRepository.deleteById(id);
    }
}
