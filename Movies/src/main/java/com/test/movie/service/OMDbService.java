package com.test.movie.service;

import com.test.movie.dto.OMDb;

/**
 * 
 * @author Fernando
 *
 */
public interface OMDbService {
	
	public OMDb findByTitle(final String title);
	
	public OMDb findByImbd(final String imbd);
}
