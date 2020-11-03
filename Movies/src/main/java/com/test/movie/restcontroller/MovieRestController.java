package com.test.movie.restcontroller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.test.movie.domain.Movie;
import com.test.movie.dto.MovieResponse;
import com.test.movie.dto.OMDb;
import com.test.movie.service.MovieService;

import lombok.AccessLevel;
import lombok.Getter;


@RestController
@RequestMapping(path = "movies/")
public class MovieRestController {


	@Getter(value = AccessLevel.PRIVATE)
	@Autowired
	private MovieService movieService;

	private JsonMapper maper;

	@GetMapping(path = "find/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findTitle(@PathVariable("title") String title) {
		try {
			OMDb omdb = this.getMovieService().findByTitle(title);
			if (!Objects.isNull(omdb)) {
				maper = new JsonMapper();
				return new ResponseEntity<String>(maper.writeValueAsString(omdb), HttpStatus.OK);
			} else {
				String response = "{ \"message\": \"Movie not found!\", \"status\": 404 }";
				return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			String response = "{ \"message\": \""+e.getMessage()+"\", \"status\": 500  }";
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@GetMapping(path = "find-watched/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findWatchedByTitle(@PathVariable("title") String title) {
		try {
			MovieResponse movieResponse = this.getMovieService().findWatchedByTitle(title);
			if (!Objects.isNull(movieResponse)) {
				maper = new JsonMapper();
				return new ResponseEntity<String>(maper.writeValueAsString(movieResponse), HttpStatus.OK);
			} else {
				String response = "{ \"message\": \"Movie not found!\", \"status\": 404 }";
				return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			String response = "{ \"message\": \""+e.getMessage()+"\", \"status\": 500  }";
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/save" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody Movie movie) throws JsonProcessingException {
		try {
			movie = this.getMovieService().save(movie);
			maper = new JsonMapper();
			return new ResponseEntity<String>(maper.writeValueAsString(movie), HttpStatus.CREATED);
		} catch (Exception e) {
			String response = "{ \"message\": \""+e.getMessage()+"\", \"status\": 412  }";
			return new ResponseEntity<String>(response, HttpStatus.PRECONDITION_FAILED);
		}
	}

	@DeleteMapping(path = "/delete/{imdbID}")
	public ResponseEntity<String> delete(@PathVariable("imdbID") String imdbID) {
		int deleted = this.getMovieService().delete(imdbID);
		if (deleted > 0) {
			String response = "{ \"message\": \"deleted!\", \"status\": 200  }";
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} else {
			String response = "{ \"message\": \"Movie not found!\", \"status\": 404 }";
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/update",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody Movie movie) throws JsonProcessingException{
		Movie movieUpdated = this.getMovieService().update(movie);
		if(!Objects.isNull(movieUpdated)) {
			maper = new JsonMapper();
			return new ResponseEntity<String>(maper.writeValueAsString(movieUpdated), HttpStatus.OK);
		}else {
			String response = "{ \"message\": \"Movie not found!\", \"status\": 404 }";
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findWatchedMovies() throws IOException {
		List<MovieResponse> list =  this.getMovieService().findAll();
		maper = new JsonMapper();
		return new ResponseEntity<String>(maper.writeValueAsString(list), HttpStatus.OK);
	}

}
