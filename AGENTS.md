# AGENTS.md

Normas de trabajo para cualquier agente (o persona) que implemente código en este proyecto (API Movies — Spring Boot). Antes de escribir código, leer `README.md` (arquitectura y decisiones) y `Planning.md` (estado y tareas pendientes).

## 1. Reglas obligatorias de proceso

### 1.1 Commits convencionales atómicos

- Cada commit representa **un único cambio lógico** y debe compilar por sí solo.
- Formato: `<tipo>(<scope>): <descripción corta en imperativo>`
- Tipos: `feat`, `fix`, `refactor`, `test`, `docs`, `chore`, `build`, `style`
- Scope = capa o recurso afectado: `repository`, `service`, `controller`, `mapper`, `dto`, `entity`, `exception`, `config`
- Ejemplos:
  - `feat(repository): añadir GeneroRepository extends JpaRepository`
  - `feat(service): implementar PeliculaServiceImpl.findById`
  - `feat(controller): añadir endpoint GET /api/v1/movies/{id}`
  - `refactor(mapper): extraer interfaz genérica Mapper<E, ReqDTO, ResDTO>`
  - `docs(readme): documentar endpoint GET /{id}`
  - `docs(planning): marcar tarea completada Fase 4`
- No mezclar cambios de varias capas o de varios endpoints en un mismo commit.
- No dejar código comentado, TODOs sin resolver, ni cambios de formato ajenos al commit.

### 1.2 Actualizar `README.md` al cerrar una iteración

- "Iteración" = ciclo completo de un endpoint (repository → service → controller → mapper/DTO si aplica) ya validado.
- Al cerrarla, documentar en `README.md`: el endpoint nuevo en "Endpoints de la API" y cualquier decisión de diseño relevante (anotación, dependencia, patrón).
- Commit dedicado: `docs(readme): ...`

### 1.3 Actualizar `Planning.md` al terminar una tarea

- Cambiar `[ ]` → `[X]` solo en la tarea concreta ya terminada, no antes.
- No marcar tareas a medias ni adelantar tareas de fases futuras.
- Si surge una tarea no prevista, añadirla a la fase correspondiente antes de marcarla.
- Commit dedicado: `docs(planning): ...`

### 1.4 Tests unitarios obligatorios por endpoint

- Ningún endpoint se considera cerrado sin sus tests unitarios correspondientes que validen el flujo horizontal completo (repository → service → controller para ESE endpoint).
- Como mínimo:
  - Test de `service` mockeando `repository` y `mapper` (según lo previsto en la Fase 9 de `Planning.md`).
  - Test de `controller` con MockMvc, mockeando la interfaz de `service`, cuando el endpoint ya expone la capa HTTP.
- Los tests se crean en el mismo ciclo del endpoint, no se posponen para el final del proyecto ni se agrupan varios endpoints en el mismo test sin distinguir casos.
- Cubrir al menos: caso de éxito y caso de error/recurso no encontrado si aplica (`ResourceNotFoundException`).
- Commit dedicado: `test(<scope>): ...` (p. ej. `test(service): cobertura PeliculaServiceImpl.findAll`).

## 2. Flujo de trabajo por endpoint: programación horizontal, no vertical

1. **Identificar el endpoint objetivo**: método HTTP + ruta, y a qué tarea de `Planning.md` corresponde. Decirlo explícitamente antes de tocar código.
2. **Trabajar en horizontal**: completar el ciclo entero de ESE endpoint (repository → service → controller → mapper/DTO si aplica) antes de tocar cualquier otro endpoint o recurso.
   - Prohibido adelantar métodos de otros endpoints "ya que se está" en esa clase.
   - Ejemplo: si se trabaja en `GET /api/v1/movies` (listar todo), solo se añade el método de repositorio, servicio y controlador necesarios para ese listado. No se añade `findById`, `save`, etc. aunque parezcan triviales.
3. **Validar antes de avanzar al siguiente endpoint**:
   - Compila sin errores ni warnings evitables.
   - Comportamiento verificado (petición de ejemplo, o test si la Fase 9 ya está activa).
   - Tests unitarios del endpoint creados y en verde (ver 1.4), validando el flujo horizontal completo.
   - Se cumplen los principios de diseño de la sección 3.
4. Solo entonces: commit(s) atómicos + `Planning.md` (tarea) + `README.md` si cierra iteración completa.

## 3. Principios de diseño a valorar ANTES de implementar

Antes de escribir una clase o método nuevo, comprobar:

- **Interfaces generales primero.** Si el componente (service, mapper) es reutilizable entre recursos (Pelicula, Genero, Actor), definir/extender una interfaz genérica (`CrudService<T, ID, ReqDTO, ResDTO>`, `Mapper<E, ReqDTO, ResDTO>`) en vez de una clase concreta suelta.
- **Inyección por constructor.** Todas las dependencias (`repository`, `mapper`, otros `service`) se inyectan por constructor, en campos `private final`. Nunca inyección por campo (`@Autowired` en atributo) ni por setter.
- **Sin sobrecarga de métodos.** No crear varias firmas del mismo método (`findAll()`, `findAll(String filtro)`...). Usar nombres explícitos distintos (`findAll()`, `findByTitulo(String titulo)`) o un objeto de criterio si hace falta combinar filtros.
- **Mappers y DTOs siempre en el borde de la API.** El controlador nunca expone ni recibe entidades JPA directamente. Toda entrada pasa por un `*RequestDTO` validado con `@Valid`; toda salida se construye con el mapper hacia un `*ResponseDTO`.

## 4. Orden de capas al implementar una petición

La petición nace y se construye en este orden:

1. **Repository** — método de acceso a datos (si no existe ya).
2. **Service** — lógica de negocio sobre la interfaz correspondiente, usando el repository inyectado.
3. **Controller** — expone el endpoint HTTP, delega en el service por interfaz (no por implementación concreta).
4. **Mapper** (si aplica) — convierte Entity ↔ DTO.
5. **DTO** — filtra y da forma al JSON de entrada/salida; la entidad JPA nunca se serializa tal cual.

No se implementa una capa superior sin que la inferior exista y funcione (p. ej., no crear el controlador antes de tener el método de servicio que necesita).

## 5. Checklist de cierre por endpoint

- [ ] Repository con el método necesario (sin métodos extra no usados).
- [ ] Service (interfaz + implementación) con inyección por constructor.
- [ ] Controller con la ruta correcta bajo `/api/v1/...` y el verbo HTTP correcto.
- [ ] DTO(s) de entrada/salida definidos y, si aplica, validados con `@Valid`.
- [ ] Mapper actualizado si hay conversión Entity ↔ DTO.
- [ ] No se ha introducido sobrecarga de métodos.
- [ ] Test(s) unitario(s) del endpoint creados y en verde (flujo horizontal validado).
- [ ] Commit(s) atómicos con Conventional Commits.
- [ ] `Planning.md`: tarea marcada `[X]`.
- [ ] `README.md` actualizado si esto cierra una iteración completa.