package ruben.dev.api_movies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ruben.dev.api_movies.entity.FilmsEntity;

public interface FilmsRepository extends JpaRepository<FilmsEntity, Long> {

    List<FilmsEntity> findByNameContainingIgnoreCase(String title);

    List<FilmsEntity> findByGenres_NameContainingIgnoreCase(String genre);
}