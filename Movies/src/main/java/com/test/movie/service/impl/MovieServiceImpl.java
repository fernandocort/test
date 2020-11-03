package com.test.movie.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.movie.domain.Movie;
import com.test.movie.dto.MovieResponse;
import com.test.movie.dto.OMDb;
import com.test.movie.repository.MovieRepository;
import com.test.movie.service.MovieService;
import com.test.movie.service.OMDbService;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	@Getter(value = AccessLevel.PRIVATE)
	private MovieRepository movieRepository;

	@Getter(value = AccessLevel.PRIVATE)
	@Autowired
	private OMDbService omdbService;

	@Override
	public OMDb findByTitle(String title) {

		OMDb omdb = this.getOmdbService().findByTitle(title);

		if (!Objects.isNull(omdb.getImdbID())) {
			log.info("omdb response0: {}", omdb);
			return omdb;
		}
		return null;
	}

	@Override
	public MovieResponse findWatchedByTitle(String title) {

		OMDb omdb = this.getOmdbService().findByTitle(title);

		if (!Objects.isNull(omdb.getImdbID())) {
			log.info("omdb response0: {}", omdb);
			
			MovieResponse response = null;
			Movie movie = this.getMovieRepository().findByImdbID(omdb.getImdbID()).orElse(null);
			if (!Objects.isNull(movie)) {
				response = new MovieResponse();
				response.setImdbID(omdb.getImdbID());
				response.setComments(movie.getComments());
				response.setWatchedDate(movie.getWatchedDate());
				response.setRating(movie.getRating());
				
				response.setActors(omdb.getActors());
				response.setCountry(omdb.getCountry());
				response.setPoster(omdb.getPoster());
				response.setDirector(omdb.getDirector());
				response.setGenre(omdb.getGenre());
				response.setTitle(omdb.getTitle());
				response.setType(omdb.getType());
				response.setYear(omdb.getYear());
				
			} 
			
			return response;
		}
		return null;
	}

	@Override
	public Movie save(Movie movie) throws Exception {
		Movie movieDatabase = this.getMovieRepository().findByImdbID(movie.getImdbID()).orElse(null);
		if (Objects.isNull(movieDatabase)) {
			return this.getMovieRepository().save(movie);
		} else {
			throw new Exception("Movie already exists.");
		}

	}

	@Override
	public int delete(String imdbID) {
		Movie movie = this.getMovieRepository().findByImdbID(imdbID).orElse(null);
		if (Objects.isNull(movie)) {
			return 0;
		} else {
			this.getMovieRepository().deleteById(movie.getId());
			return 1;
		}
	}

	@Override
	public Movie update(Movie movie) {
		Movie movieUpdate = this.getMovieRepository().findByImdbID(movie.getImdbID()).orElse(null);
		if (Objects.isNull(movieUpdate)) {
			return null;
		} else {
			movieUpdate.setComments(movie.getComments());
			movieUpdate.setRating(movie.getRating());
			movieUpdate.setWatchedDate(movie.getWatchedDate());
			movieUpdate = this.getMovieRepository().saveAndFlush(movieUpdate);
			return movieUpdate;
		}

	}

	@Override
	public List<MovieResponse> findAll() {
		List<MovieResponse> responseList = new ArrayList<>();
		this.getMovieRepository().findAll().forEach(movie -> {
			OMDb ombd = this.getOmdbService().findByImbd(movie.getImdbID());
			MovieResponse obj = new MovieResponse();
			obj.setImdbID(movie.getImdbID());
			obj.setRating(movie.getRating());
			obj.setComments(movie.getComments());
			obj.setWatchedDate(movie.getWatchedDate());
			obj.setActors(ombd.getActors());
			obj.setCountry(ombd.getCountry());
			obj.setPoster(ombd.getPoster());
			obj.setDirector(ombd.getDirector());
			obj.setGenre(ombd.getGenre());
			obj.setTitle(ombd.getTitle());
			obj.setType(ombd.getType());
			obj.setYear(ombd.getYear());
			responseList.add(obj);
		});
		return responseList;
	}
}
