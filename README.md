# API Movies

## Endpoints de la API

- Obtener todas las películas.
- Obtener una película por ID.
- Añadir una película.
- Actualizar una película.
- Eliminar una película.
- Buscar una película por título o género *(opcional)*.

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
- `org.testcontainers:testcontainers-junit-jupiter`: soporte de JUnit Jupiter con Testcontainers.
- `org.testcontainers:testcontainers-mysql`: contenedor MySQL para pruebas.

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
            ├── mapper/        // Conversion entre Entity, DTO y View
            ├── dtos/           // Transporta y prepara los datos entre capas
            ├── entity/        // Representa las tablas y datos de la base de datos
            └── exception/    // Define y gestiona los errores de la aplicación
```

## Base de datos

He razonado 5 tablas:

- Un género puede tener muchas películas.
- Una película puede tener varios géneros.
- Una película puede tener muchos actores.
- Un actor puede participar en muchas películas.

La relación entre PELICULA y GENERO es N:N,
por lo que se utilizará una tabla intermedia llamada `pelicula_genero`.

La relación entre PELICULA y ACTOR es N:N,
por lo que se utilizará una tabla intermedia llamada `pelicula_actor`.

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

### Tabla intermedia `pelicula_genero`

- `pelicula_id`: clave primaria y clave foránea.
- `genero_id`: clave primaria y clave foránea.

### Tabla intermedia `actor_pelicula`

- `actor_id`: clave primaria y clave foránea.
- `pelicula_id`: clave primaria y clave foránea.

En ambas tablas intermedias se utilizará una clave primaria compuesta
formada por las dos claves foráneas. Esto evita que una misma relación
entre una película y un género, o entre una película y un actor,
pueda registrarse más de una vez.


## Resolución

Empecé configurando los .properties para la DB y creando la estructura de trabajo especificada en [Estructura-de-proyecto]. Acto seguido modifique compose.yaml para el docker environment con la DB.

Creé las entities:
- Films
- Genre
- Actors