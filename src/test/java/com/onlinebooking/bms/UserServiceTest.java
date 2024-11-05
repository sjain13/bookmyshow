package com.onlinebooking.bms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.onlinebooking.bms.dto.UserDto;
import com.onlinebooking.bms.exception.DuplicateRecordException;
import com.onlinebooking.bms.repository.UserRepository;
import com.onlinebooking.bms.service.UserService;

import jakarta.transaction.Transactional;


@SpringBootTest
@Transactional // Ensure each test method runs within a transaction
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        deleteUser(); // Clear existing user before each test
    }

    @Test
    public void testAddUser() {
        String name = "Test";
        String mobile = "124";

        UserDto userDto = UserDto.builder().name(name).mobile(mobile).build();

        userDto = userService.addUser(userDto);

        assertNotNull(userDto);
        assertEquals(name, userDto.getName());
        assertEquals(mobile, userDto.getMobile());

        // No need to delete here if you are using @Transactional
    }

    @Test
    public void testAddDuplicateUser() {
        String name = "Test";
        String mobile = "124";

        UserDto userDto = UserDto.builder().name(name).mobile(mobile).build();
        userService.addUser(userDto); // Add the first user

        // Expecting DuplicateRecordException to be thrown
        assertThrows(DuplicateRecordException.class, () -> {
            userService.addUser(userDto); // Try to add duplicate
        });
    }

    @Test
    public void testGetUser() {
        String name = "Test";
        String mobile = "124";

        UserDto userDto = UserDto.builder().name(name).mobile(mobile).build();
        userDto = userService.addUser(userDto);

        userDto = userService.getUser(userDto.getId());

        assertNotNull(userDto);
        assertEquals(name, userDto.getName());
        assertEquals(mobile, userDto.getMobile());

        // No need to delete here if you are using @Transactional
    }

    private void deleteUser() {
        userRepository.deleteByMobile("124");
    }
}
