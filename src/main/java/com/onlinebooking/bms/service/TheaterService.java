/**
 * 
 */
package com.onlinebooking.bms.service;

import com.onlinebooking.bms.dto.TheaterDto;


public interface TheaterService {

	TheaterDto addTheater(TheaterDto theaterDto);

	TheaterDto getTheater(long id);
}