package com.parpiiev.time.services.implementations;

import com.parpiiev.time.model.User;
import com.parpiiev.time.repository.UserRepository;
import com.parpiiev.time.services.interfaces.UserService;
import com.parpiiev.time.utils.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class UserServiceImplIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService<UserDTO> userService;


    @BeforeEach
    void setUp() {
        User user = new User("name", "ui", "pass", "admin");
        User user1 = new User("NAME", "ol", "pa1", "user");
        User user2 = new User("test", "tt", "s2", "user");
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void testLoadDataForTestClass() {
        assertFalse(userRepository.findAll().isEmpty());
        assertEquals(3, userRepository.findAll().size());
    }

    @Test
    void testGetById() {

        Optional<UserDTO> user = userService.getById(1);
        assertTrue(user.isPresent());
        assertEquals("admin", user.get().getRole());
    }

    @Test
    void testGetAll() {
        assertFalse(userService.getAll().isEmpty());
        assertEquals(3, userService.getAll().size());
    }

    @Test
    void testSave() {
        UserDTO userDto = new UserDTO(4,"name3", "kl", "pass3", "user");
        userService.save(userDto);
        assertEquals(4, userService.getAll().size());
        assertTrue(userService.getById(4).isPresent());
        assertEquals("name3", userService.getById(4).get().getName());
    }

    @Test
    void testGetByLogin() {
        assertTrue(userService.getByLogin("ui").isPresent());
        assertFalse(userService.getByLogin("a").isPresent());
    }

    @Test
    void testGetUserRole() {
        UserDTO userDto = new UserDTO(1,"name", "ui", "pass", "admin");
        assertNotNull(userService.getUserRole(userDto));
        assertEquals("admin", userService.getUserRole(userDto));
    }

    @Test
    void testDelete() {
        assertEquals(3, userService.getAll().size());
        userService.delete(3);
        assertEquals(2, userService.getAll().size());
    }

}