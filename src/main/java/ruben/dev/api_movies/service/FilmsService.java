package ruben.dev.api_movies.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    public Optional<FilmsEntity> findById(long id){
        return filmsRepository.findById(id);
    }

    public FilmsEntity save(FilmsEntity film) {
        return filmsRepository.save(film);
    }

    public void deleteById(Long id) {
        filmsRepository.deleteById(id);
    }
}
