package ruben.dev.api_movies.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "films")
public class FilmsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_film;
    private String name;
    private String description;
    private LocalDate date;

    public FilmsEntity(){

    }

    public FilmsEntity(Long id, String name, String description, LocalDate date) {
        this.id_film = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id_film;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    
}
