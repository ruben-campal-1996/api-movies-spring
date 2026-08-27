package ruben.dev.api_movies.service;

import org.springframework.stereotype.Service;

import ruben.dev.api_movies.repository.FilmsRepository;

@Service
public class FilmsService {
    private final FilmsRepository filmsRepository;

    public FilmsService(FilmsRepository filmsRepository) {
        this.filmsRepository = filmsRepository;
    }

}
