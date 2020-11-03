package com.test.movie.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 
 * @author Fernando
 *
 */

@Data
@Entity
@Table
public class Movie {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 50)
	private String imdbID;
	@Column(length = 100)
	private String comments;
	@Column(length = 50)
	private String rating;

	@Column(name = "watched_date", length = 50)
	private String watchedDate;

	
	
}
