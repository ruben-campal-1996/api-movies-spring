package ruben.dev.api_movies.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import ruben.dev.api_movies.dtos.FilmsResponseDTO;
import ruben.dev.api_movies.entity.FilmsEntity;

@Component
public class FilmsMapper {

    public FilmsResponseDTO toResponseDto(FilmsEntity film) {
        if (film == null) {
            return null;
        }

        Integer releaseYear = film.getYear() != null ? film.getYear().getYear() : null;
        return new FilmsResponseDTO(film.getId(), film.getName(), film.getDescription(), releaseYear);
    }

    public List<FilmsResponseDTO> toResponseDtoList(List<FilmsEntity> films) {
        return films.stream()
            .map(this::toResponseDto)
            .toList();
    }
}
