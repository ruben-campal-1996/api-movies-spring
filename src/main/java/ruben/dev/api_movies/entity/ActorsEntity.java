package ruben.dev.api_movies.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "actors")
public class ActorsEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id_actor;
private String name;



public ActorsEntity() {
}

public ActorsEntity(Long id, String name) {
    this.id_actor = id;
    this.name = name;
}

public long getId_actor() {
    return id_actor;
}

public String getName() {
    return name;
}




}
