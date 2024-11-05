/**
 * 
 */
package com.onlinebooking.bms.adapter;

import com.onlinebooking.bms.dto.UserDto;
import com.onlinebooking.bms.model.UserEntity;

import lombok.experimental.UtilityClass;


@UtilityClass
public class UserAdapter {

	public static UserEntity toEntity(UserDto userDto) {

		return UserEntity.builder()
				.name(userDto.getName())
				.mobile(userDto.getMobile())
				.build();

	}

	public static UserDto toDto(UserEntity userEntity) {

		return UserDto.builder()
				.id(userEntity.getId())
				.name(userEntity.getName())
				.mobile(userEntity.getMobile())
				.build();
	}

}