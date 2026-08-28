package ruben.dev.api_movies.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "films")
public class FilmsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_film;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_year", referencedColumnName = "id_year")
    private YearsEntity year;

    @ManyToMany
    @JoinTable(
        name = "genero_pelicula",
        joinColumns = @JoinColumn(
            name = "id_pelicula",
            referencedColumnName = "id_film"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "id_genero",
            referencedColumnName = "id_genre"
        )
    )
    private Set<GenreEntity> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "actor_pelicula",
        joinColumns = @JoinColumn(
            name = "id_pelicula",
            referencedColumnName = "id_film"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "id_actor",
            referencedColumnName = "id_actor"
        )
    )
    private Set<ActorsEntity> actors = new HashSet<>();

    public FilmsEntity(){

    }

    public FilmsEntity(Long id, String name, String description, YearsEntity year) {
        this.id_film = id;
        this.name = name;
        this.description = description;
        this.year = year;
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

    public YearsEntity getYear() {
        return year;
    }

    public Set<GenreEntity> getGenres() {
        return genres;
    }

    public Set<ActorsEntity> getActors() {
        return actors;
    }

    
}
