package ruben.dev.api_movies.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "genre")
public class GenreEntity {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private long id_genre;
private String name;


public class Genreentity {

}

public GenreEntity(Long id, String name) {
    this.id_genre = id;
    this.name = name;
}

public long getId_genre() {
    return id_genre;
}

public String getName() {
    return name;
}



}
