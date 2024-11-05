/**
 * 
 */
package com.onlinebooking.bms.service;

import com.onlinebooking.bms.dto.MovieDto;


public interface MovieService {

	MovieDto addMovie(MovieDto movieDto);

	MovieDto getMovie(long id);
}