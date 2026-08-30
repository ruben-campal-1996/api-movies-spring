package ruben.dev.api_movies.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ruben.dev.api_movies.dtos.FilmsRequestDTO;
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

    @PostMapping
    public ResponseEntity<FilmsResponseDTO> save(@Valid @RequestBody FilmsRequestDTO request) {
        FilmsResponseDTO response = filmsService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public FilmsResponseDTO update(@PathVariable Long id, @Valid @RequestBody FilmsRequestDTO request) {
        return filmsService.update(id, request);
    }
}
