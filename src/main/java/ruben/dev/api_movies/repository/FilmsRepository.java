package ruben.dev.api_movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ruben.dev.api_movies.entity.FilmsEntity;

public interface FilmsRepository extends JpaRepository<FilmsEntity, Long> {
}
