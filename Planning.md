# Plan de tareas actualizado

## Fase 1 – Configuración inicial

- [X] Crear el proyecto con Spring Initializr (Web, JPA, Validation, H2, Test, Lombok opcional).
- [X] Configurar `application.properties` (perfil H2, datasource H2, ddl-auto y consola H2).
- [X] Crear la estructura de paquetes: `config`, `view`, `controller`, `service`, `repository`, `mapper`, `dtos`, `entity`, `exception`.
- [ ] Verificar que el proyecto arranca y la consola H2 es accesible.

## Fase 2 – Entidades

- [X] Crear entidad `Pelicula` (`FilmsEntity`: id, name, description y relación con `YearsEntity`).
- [X] Crear entidad `Genero` (`GenreEntity`: id, name).
- [X] Crear entidad `Actor` (`ActorsEntity`: id, name).
- [ ] Crear clase `@Embeddable` `PeliculaGeneroId` (peliculaId, generoId).
- [ ] Crear clase `@Embeddable` `PeliculaActorId` (peliculaId, actorId) — nombre unificado.
- [ ] Crear entidad intermedia `PeliculaGenero` con `@EmbeddedId` y relaciones `@ManyToOne`.
- [ ] Crear entidad intermedia `PeliculaActor` con `@EmbeddedId` y relaciones `@ManyToOne`.

> **Nota:** Actualmente las relaciones N:N se gestionan directamente con `@ManyToMany` + `@JoinTable` en `FilmsEntity`. Las tablas intermedias con `@Embeddable` están pendientes según el diseño del plan.

## Fase 3 – Repositorios

- [X] Crear `PeliculaRepository extends JpaRepository` (interfaz, Spring Data ya la implementa por ti — DI automática vía constructor donde se use).
- [ ] Crear `GeneroRepository extends JpaRepository`.
- [ ] Crear `ActorRepository extends JpaRepository`.
- [ ] Añadir métodos de consulta para búsqueda (`findByTituloContainingIgnoreCase`, búsqueda por género).

## Fase 4 – DTOs

- [X] Crear `FilmsRequestDTO` con validaciones (`@NotBlank`, `@NotNull`).
- [X] Crear `FilmsResponseDTO` para la respuesta del endpoint `/movies`.
- [ ] Crear `GeneroDTO` y `ActorDTO`.
- [X] Crear `ApiErrorDTO` (código, mensaje, timestamp) para respuestas de error.

## Fase 5 – Mappers (con interfaz)

- [ ] Definir interfaz genérica `Mapper<E, ReqDTO, ResDTO>` (métodos `toEntity`, `toResponseDTO`, `toResponseDTOList`).
- [X] Implementar `FilmsMapper` para convertir `FilmsEntity` a `FilmsResponseDTO`.
- [ ] Implementar `GeneroMapper` y `ActorMapper` sobre la misma interfaz.

## Fase 6 – Servicios (con interfaz e inyección por constructor)

- [X] Crear `FilmsService` como interfaz y `FilmsServiceImpl` con inyección por constructor.
- [ ] Definir interfaz genérica `CrudService<T, ID, ReqDTO, ResDTO>` con las operaciones CRUD comunes.
- [ ] Crear interfaz `PeliculaService extends CrudService<...>` añadiendo el método de búsqueda.
- [ ] Implementar `PeliculaServiceImpl` inyectando `PeliculaRepository`, `GeneroRepository`, `ActorRepository` y `PeliculaMapper` por constructor (campos `private final`).
- [ ] Crear interfaz `GeneroService extends CrudService<...>` e implementación con inyección por constructor.
- [ ] Crear interfaz `ActorService extends CrudService<...>` e implementación con inyección por constructor.
- [ ] Implementar en `PeliculaServiceImpl` la lógica para asociar géneros y actores al crear/actualizar una película.
- [X] Lanzar `ResourceNotFoundException` cuando no se encuentre un recurso por ID en los servicios para el caso de `GET /movies`.

## Fase 7 – Excepciones

- [X] Crear `ResourceNotFoundException`.
- [ ] Crear una segunda excepción personalizada (p. ej. `InvalidRequestException`) si la necesitas.
- [X] Crear `GlobalExceptionHandler` con `@RestControllerAdvice` para las excepciones personalizadas.
- [X] Añadir en `GlobalExceptionHandler` el manejo de `MethodArgumentNotValidException` (errores de `@Valid`).

## Fase 8 – Controladores (dependiendo de interfaces, no de implementaciones)

- [X] Crear `FilmsController` (`@RestController`, base path `/api/v1/movies`) con inyección de `FilmsService`.
- [X] Implementar `GET /` (obtener todas las películas).
- [X] Implementar `GET /{id}`.
- [X] Implementar `POST /` (crear, con `@Valid @RequestBody`).
- [ ] Implementar `PUT /{id}` (o PATCH) y `DELETE /{id}`.
- [ ] Implementar `GET /buscar` (por título/género) y crear controladores equivalentes mínimos para Genero/Actor si se necesitan como recursos propios.

## Fase 9 – Testing

- [X] Escribir tests unitarios de `FilmsServiceImpl` mockeando `FilmsRepository` y `FilmsMapper`.
- [X] Escribir tests de controller con MockMvc para `GET /api/v1/movies` y sus errores.
- [X] Añadir tests del flujo `POST /api/v1/movies` para caso válido e inválido.

> Estado actual: `./mvnw test -Dtest=FilmsServiceTest,FilmsControllerTest --no-transfer-progress` pasa con 11 tests ejecutados y 0 errores.
