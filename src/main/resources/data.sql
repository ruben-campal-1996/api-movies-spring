INSERT INTO genre (id_genre, name) VALUES
	(1, 'Ciencia ficcion'),
	(2, 'Drama'),
	(3, 'Aventura');

INSERT INTO actors (id_actor, name) VALUES
	(1, 'Sigourney Weaver'),
	(2, 'Tom Hanks'),
	(3, 'Harrison Ford');

INSERT INTO films (id_film, name, description, date) VALUES
	(1, 'Alien', 'Una tripulacion espacial se enfrenta a una criatura desconocida.', '1979-05-25'),
	(2, 'Forrest Gump', 'La vida extraordinaria de un hombre con una mirada sencilla del mundo.', '1994-07-06'),
	(3, 'Indiana Jones', 'Un arqueologo se embarca en una aventura para encontrar un antiguo tesoro.', '1981-06-12');

INSERT INTO genero_pelicula (id_pelicula, id_genero) VALUES
	(1, 1),
	(2, 2),
	(3, 3);

INSERT INTO actor_pelicula (id_actor, id_pelicula) VALUES
	(1, 1),
	(2, 2),
	(3, 3);
