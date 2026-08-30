package ruben.dev.api_movies.dtos;

public class FilmsResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Integer releaseYear;

    public FilmsResponseDTO() {
    }

    public FilmsResponseDTO(Long id, String name, String description, Integer releaseYear) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseYear = releaseYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}
