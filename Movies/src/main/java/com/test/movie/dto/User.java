package com.test.movie.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2801095561219021614L;
	private Long id;
	private String username;
	private String password;
	
}
