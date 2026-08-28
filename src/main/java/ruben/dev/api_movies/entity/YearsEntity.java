package ruben.dev.api_movies.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "release_years")
public class YearsEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_year;
    @Column(name = "release_year")
    private Integer year;

    @OneToMany(mappedBy = "year")
    @JsonIgnore
    private Set<FilmsEntity> films = new HashSet<>();

    public YearsEntity() {
    }

    public YearsEntity(Long id, int year) {
        this.id_year = id;
        this.year = year;
    }

    public long getId_year() {
        return id_year;
    }

    public Integer getYear() {
        return year;
    }

    public Set<FilmsEntity> getFilms() {
        return films;
    }
}
