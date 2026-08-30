package ruben.dev.api_movies.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ruben.dev.api_movies.entity.YearsEntity;

public interface YearsRepository extends JpaRepository<YearsEntity, Long> {

    Optional<YearsEntity> findByYear(Integer year);
}
