package com.test.movie.service;

import java.util.List;

import com.test.movie.domain.Movie;
import com.test.movie.dto.MovieResponse;
import com.test.movie.dto.OMDb;

public interface MovieService {
	
	public OMDb findByTitle(final String title);
	
	public MovieResponse findWatchedByTitle(final String title);
	
	
	
	public Movie save(final Movie movie) throws Exception;
	
	public Movie update(final Movie movie);
	
	public int delete(final String imdbID);
	
	public List<MovieResponse> findAll();

}
