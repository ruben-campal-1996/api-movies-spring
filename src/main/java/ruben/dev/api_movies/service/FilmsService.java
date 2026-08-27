package ruben.dev.api_movies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ruben.dev.api_movies.entity.FilmsEntity;
import ruben.dev.api_movies.repository.FilmsRepository;

@Service
public class FilmsService {
    private final FilmsRepository filmsRepository;

    public FilmsService(FilmsRepository filmsRepository) {
        this.filmsRepository = filmsRepository;
    }

    public List<FilmsEntity> findAll() {
        return filmsRepository.findAll();
    }

}
