package com.test.movie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.movie.domain.Movie;

/**
 * 
 * @author Fernando
 *
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>{
	
	@Query(value = "select m from Movie m where m.imdbID = :imdbID ")
	Optional<Movie> findByImdbID(@Param("imdbID") String imdbID);

}
