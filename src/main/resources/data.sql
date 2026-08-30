INSERT INTO genre (name) VALUES
	('Ciencia ficcion'),
	('Drama'),
	('Aventura');

INSERT INTO actors (name) VALUES
	('Sigourney Weaver'),
	('Tom Hanks'),
	('Harrison Ford');

INSERT INTO release_years (release_year) VALUES
	(1979),
	(1994),
	(1981);

INSERT INTO films (name, description, id_year) VALUES
	('Alien', 'Una tripulacion espacial se enfrenta a una criatura desconocida.', 1),
	('Forrest Gump', 'La vida extraordinaria de un hombre con una mirada sencilla del mundo.', 2),
	('Indiana Jones', 'Un arqueologo se embarca en una aventura para encontrar un antiguo tesoro.', 3);

INSERT INTO genero_pelicula (id_pelicula, id_genero) VALUES
	(1, 1),
	(2, 2),
	(3, 3);

INSERT INTO actor_pelicula (id_actor, id_pelicula) VALUES
	(1, 1),
	(2, 2),
	(3, 3);