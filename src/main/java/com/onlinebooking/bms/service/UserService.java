/**
 * 
 */
package com.onlinebooking.bms.service;

import com.onlinebooking.bms.dto.UserDto;

public interface UserService {

	UserDto addUser(UserDto userDto);

	UserDto getUser(long id);
}