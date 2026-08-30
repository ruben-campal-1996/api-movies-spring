# API Movies

## Endpoints de la API

- Obtener todas las películas: `GET /api/v1/movies`.
- Obtener una película por ID: `GET /api/v1/movies/{id}`.
- Añadir una película: `POST /api/v1/movies` con `@Valid` en `FilmsRequestDTO`.
- Actualizar una película: `PUT /api/v1/movies/{id}` con validación y manejo de 404.
- Buscar una película por título o género: `GET /api/v1/movies/search?title=...` o `GET /api/v1/movies/search?genre=...`.

### Diseño de la creación y actualización de películas

La entrada de la API usa `FilmsRequestDTO` como frontera de entrada y `FilmsResponseDTO` como salida del controlador. La conversión entre entidad y DTO se resuelve con `FilmsMapper`, mientras la lógica de negocio queda en `FilmsServiceImpl`, que recibe por constructor `FilmsRepository`, `FilmsMapper` y `YearsRepository`.

Este diseño evita exponer la entidad JPA directamente en la capa HTTP y mantiene la validación de Spring en el controlador. El servicio realiza la operación bajo una transacción para garantizar la consistencia de la película y su año asociado.

## Recursos de Spring

Dependencias instaladas en `pom.xml`:

### Producción

- `spring-boot-h2console`: habilita la consola web de H2 para consultar la base de datos.
- `spring-boot-starter-actuator`: añade endpoints de monitorización y salud del servicio.
- `spring-boot-starter-data-jpa`: permite acceder a la base de datos mediante JPA.
- `spring-boot-starter-validation`: valida los datos recibidos en controladores y DTOs.
- `spring-boot-starter-webmvc`: crea la API REST con Spring MVC.
- `spring-boot-devtools`: facilita el desarrollo con recarga automática.
- `spring-boot-docker-compose`: integra Docker Compose con la aplicación en entorno de desarrollo.
- `com.h2database:h2`: base de datos en memoria para pruebas y desarrollo local.
- `com.mysql:mysql-connector-j`: driver para conectar con MySQL.

### Test

- `spring-boot-starter-actuator-test`: utilidades de test para Actuator.
- `spring-boot-starter-data-jpa-test`: soporte para pruebas con JPA.
- `spring-boot-starter-validation-test`: soporte para pruebas de validación.
- `spring-boot-starter-webmvc-test`: pruebas del controlador y MVC.
- `spring-boot-testcontainers`: integración con Testcontainers para pruebas de contenedores.
- `org.testcontainers:testcontainers-junit-jupiter`: soporte para JUnit Jupiter con Testcontainers.
- `org.testcontainers:testcontainers-mysql`: contenedor MySQL para pruebas.

### Inyección de dependencias

Spring crea automáticamente los objetos gestionados como beans y resuelve sus dependencias. No es necesario instanciar manualmente los constructores desde `AppContainer`.

Se recomienda utilizar inyección por constructor:

```java
@Service
public class FilmsService {

    private final FilmsRepository filmsRepository;

    public FilmsService(FilmsRepository filmsRepository) {
        this.filmsRepository = filmsRepository;
    }
}
```

`AppContainer` configura el contexto de Spring Boot y escanea los componentes del proyecto:

```java
@SpringBootApplication(scanBasePackages = "ruben.dev.api_movies")
public class AppContainer {
}
```

La clase `AppContainer` no debe crear manualmente los servicios, repositorios ni controladores. Solo se utiliza `@Bean` cuando sea necesario registrar explícitamente un objeto que Spring no pueda detectar automáticamente, como una configuración de una librería externa.

## Anotaciones

- `@RestController`: la clase expone una API REST.
- `@RequestMapping`: define una ruta base.
- `@GetMapping`: endpoint GET.
- `@PostMapping`: endpoint POST.
- `@PutMapping`: endpoint PUT.
- `@PatchMapping`: endpoint PATCH.
- `@DeleteMapping`: endpoint DELETE.
- `@PathVariable`: obtiene variables de la URL.
- `@RequestParam`: obtiene parámetros.
- `@RequestBody`: convierte el JSON recibido en un objeto Java.
- `@ResponseStatus`: permite especificar el estado HTTP.

## Validaciones

- `@Valid`
- `@NotNull`
- `@NotBlank`
- `@NotEmpty`
- `@Size`
- `@Min`
- `@Max`
- `@Email`
- `@Pattern`

## Excepciones

- `@RestControllerAdvice`
- `@ControllerAdvice`
- `@ExceptionHandler`
- `ResponseEntity`
- `HttpStatus`

## Estructura del proyecto

```text
src/
└── main/
    └── java/
        └── ruben.dev.api_movies/
            ├── config/        // Configuración de la aplicación y de Spring
            ├── view/          // Gestiona la presentación de los datos al usuario
            ├── controller/    // Gestiona las peticiones HTTP y coordina el flujo
            ├── service/       // Contiene la lógica de negocio
            ├── repository/    // Accede a la base de datos mediante JPA
            ├── mapper/        // Conversión entre Entity, DTO y View
            ├── dtos/          // Transporta y prepara los datos entre capas
            ├── entity/        // Representa las tablas y datos de la base de datos
            └── exception/     // Define y gestiona los errores de la aplicación
```

## Base de datos

He razonado cinco tablas:

- Un género puede tener muchas películas.
- Una película puede tener varios géneros.
- Una película puede tener muchos actores.
- Un actor puede participar en muchas películas.

La relación entre `PELICULA` y `GENERO` es N:N, por lo que se utilizará una tabla intermedia llamada `genero_pelicula`.

La relación entre `PELICULA` y `ACTOR` es N:N, por lo que se utilizará una tabla intermedia llamada `actor_pelicula`.

### Película

- `id`: clave primaria.
- `titulo`.
- `descripcion`.
- `fecha_lanzamiento`.

### Género

- `id`: clave primaria.
- `nombre`.

### Actor

- `id`: clave primaria.
- `nombre`.

### Tabla intermedia `genero_pelicula`

- `id_genero`: clave primaria y clave foránea.
- `id_pelicula`: clave primaria y clave foránea.

### Tabla intermedia `actor_pelicula`

- `id_actor`: clave primaria y clave foránea.
- `id_pelicula`: clave primaria y clave foránea.

En ambas tablas intermedias se utilizará una clave primaria compuesta formada por las dos claves foráneas. Esto evita que una misma relación entre una película y un género, o entre una película y un actor, pueda registrarse más de una vez.

## Resolución

### Configuración inicial

Empecé configurando los archivos `.properties` para la base de datos y creando la estructura de trabajo especificada en la estructura del proyecto. Acto seguido, preparé la aplicación para trabajar con H2 en entorno local y con la base de datos relacional necesaria para las entidades y relaciones.

### Entidades

Creé las entidades:

- `FilmsEntity`
- `GenreEntity`
- `ActorsEntity`
- `YearsEntity`

La relación entre película y año se modela como `@ManyToOne`, donde cada película apunta a un año de lanzamiento. Ese vínculo se gestiona mediante `YearsRepository`, ya que la entidad `YearsEntity` necesita estar persistida y gestionada por JPA para evitar conflictos de integridad.

### Variables de entorno

Configuré las variables de entorno desde `Run > Add Configuration > env` para permitir la inicialización local con H2.

### Liberar el puerto

Si el proyecto no se cierra correctamente y deja el puerto ocupado, se puede ejecutar en Git Bash:

```bash
netstat -ano | findstr :8080
MSYS_NO_PATHCONV=1 taskkill /PID 123456 /F //123456 = código del proceso que lo mantiene activo
```

Configuré `FilmsRepository` extendiéndolo de `JpaRepository<FilmsEntity, Long>`. De este modo, relacionamos el repository con la entidad `FilmsEntity` y con el tipo de su identificador (`Long`). Spring Data JPA genera automáticamente su implementación y proporciona las operaciones CRUD, por lo que no necesitamos escribirlas manualmente. **NOTA**: No hace falta añadir `@Repository`, porque `JpaRepository` ya se registra automáticamente como bean singleton.

Configuré `FilmsServiceImpl` como un bean mediante `@Service`. Recibe por constructor `FilmsRepository`, `FilmsMapper` y `YearsRepository`, manteniendo las dependencias en campos `final`. Sus métodos delegan en el repository las operaciones sobre las películas y se apoyan en `YearsRepository` para reutilizar o crear el `YearsEntity` asociado al año de estreno.

### Solución al problema de persistencia con `YearsEntity`

El problema principal surgió al persistir una película con su año de estreno. Si se construía una nueva entidad `YearsEntity` sin gestionarla desde el repositorio, Hibernate la trataba como una entidad detached y fallaba con errores del tipo:

- `Detached entity passed to persist`
- `Unique index or primary key violation` en `release_years`

La solución final fue:

```java
YearsEntity year = yearsRepository.findByYear(request.getReleaseYear())
    .orElseGet(() -> yearsRepository.save(new YearsEntity(null, request.getReleaseYear())));
```

De esta forma, el año queda siempre gestionado por JPA y no entra en conflicto con registros ya existentes. Además, las operaciones de escritura se ejecutan con `@Transactional` para garantizar la consistencia del conjunto película + año.

### Búsqueda por título o género

La búsqueda no reutiliza ninguna sobrecarga de métodos. Se mantiene un único método `searchByTitleOrGenre(String title, String genre)` y se construye la respuesta a partir de la combinación de resultados de `filmsRepository` con `findByNameContainingIgnoreCase` y `findByGenres_NameContainingIgnoreCase`. Si no hay coincidencias, el servicio lanza `ResourceNotFoundException` con el mensaje esperado por el controlador.

