package com.test.movie.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.movie.dto.OMDb;
import com.test.movie.service.OMDbService;

/**
 * 
 * @author Fernando
 *
 */
@Qualifier(value = "omdbService")
@Service
public class OMDbServiceImpl implements OMDbService{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${omdbapi.url}")
	private String omdbApiUrl; 
	
	@Value("${omdbapi.key}")
	private String omdbApiKey;

	@Override
	public OMDb findByTitle(String title)  {

		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(omdbApiUrl).queryParam("t", title)
		            .queryParam("apikey", omdbApiKey);

		 return this.restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET, null, 
					OMDb.class).getBody();
	}

	@Override
	public OMDb findByImbd(String imbd) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(omdbApiUrl).queryParam("i", imbd)
	            .queryParam("apikey", omdbApiKey);

	 return this.restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET, null, 
				OMDb.class).getBody();
	}
	

}
