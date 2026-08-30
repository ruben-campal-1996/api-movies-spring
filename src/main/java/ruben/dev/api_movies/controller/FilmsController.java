package ruben.dev.api_movies.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ruben.dev.api_movies.dtos.FilmsResponseDTO;
import ruben.dev.api_movies.service.FilmsService;

@RestController
@RequestMapping("/api/v1/movies")
public class FilmsController {

    private final FilmsService filmsService;

    public FilmsController(FilmsService filmsService){
        this.filmsService = filmsService;
    }

    @GetMapping
    public List<FilmsResponseDTO> findAll() {
        return filmsService.findAll();
    }

    @GetMapping("/{id}")
    public FilmsResponseDTO findById(@PathVariable Long id) {
        return filmsService.findById(id);
    }

}
