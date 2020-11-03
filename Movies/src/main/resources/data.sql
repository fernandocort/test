DROP TABLE IF EXISTS Movie;

CREATE TABLE Movie(
	id INT AUTO_INCREMENT PRIMARY KEY,
 	imdbID VARCHAR(50) NOT NULL,
 	rating VARCHAR(50),
 	watched_date VARCHAR(50),
 	comments VARCHAR(100)
);


INSERT INTO Movie(imdbID, rating, watched_date, comments) VALUES ('tt0936501', '7.8', '2010-12-24 00:00:00', 'Buena película de acción y pelea.');