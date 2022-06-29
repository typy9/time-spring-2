package com.parpiiev.time.services.implementations;

import com.parpiiev.time.model.Activity;
import com.parpiiev.time.model.Category;
import com.parpiiev.time.model.User;
import com.parpiiev.time.model.UsersActivity;
import com.parpiiev.time.repository.ActivityRepository;
import com.parpiiev.time.repository.CategoryRepository;
import com.parpiiev.time.repository.UserRepository;
import com.parpiiev.time.repository.UsersActivityRepository;
import com.parpiiev.time.services.interfaces.ActivityService;
import com.parpiiev.time.services.interfaces.UsersActivityService;
import com.parpiiev.time.utils.dto.UserDTO;
import com.parpiiev.time.utils.dto.UsersActivityDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class UsersActivityServiceImplIntegrationTest {

    @Autowired
    private UsersActivityRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UsersActivityService<UsersActivityDTO> service;


    @BeforeEach
    void setUp() {

        Category category = new Category("management");
        categoryRepository.save(category);

        Activity activity = new Activity("general", category);
        Activity activity1 = new Activity("test", category);
        activityRepository.save(activity);
        activityRepository.save(activity1);

        User user = new User("name", "log", "pass", "user");
        userRepository.save(user);

        UsersActivity userActivity =
                new UsersActivity(1, user, activity, 360);
        UsersActivity userActivity1 =
                new UsersActivity(2, user, activity1, 0);

        List<UsersActivity> usersActivitiesList = new ArrayList<>();
        usersActivitiesList.add(userActivity);
        usersActivitiesList.add(userActivity1);

        repository.saveAll(usersActivitiesList);
    }


    @Test
    void testGetById() {
        Optional<UsersActivityDTO> record = service.getById(1);
        assertTrue(record.isPresent());
        assertEquals(360, record.get().getTime());
    }

    @Test
    void testGetAll() {
        assertFalse(service.getAll().isEmpty());
        assertEquals(2, service.getAll().size());
    }

//    @Test
//    void testSave() {
//        UsersActivityDTO dto = new UsersActivityDTO();
//        dto.setUser_id(1);
//        dto.setActivity_id(2);
//        dto.setTime(30);
//        service.save(dto);
//        assertEquals(3, service.getAll().size());
//        assertTrue(service.getById(3).isPresent());
//        assertEquals(30, service.getById(3).get().getTime());
//    }

//    @Test
//    void testUpdate() {
//        UsersActivityDTO dto = new UsersActivityDTO();
//
//        dto.setUser_id(user.getId());
//        dto.setActivity_id(activity.getId());
//        dto.setTime(60);
//
//        System.out.println("userActivity : " + dto);
//
//        service.update(dto);
//        assertTrue(service.getById(1).isPresent());
//        assertEquals(60, service.getById(1).get().getTime());
//    }

    @Test
    void testDelete() {
        assertEquals(2, service.getAll().size());
        service.delete(2);
        assertEquals(1, service.getAll().size());
    }

    @Test
    void testGetAllById() {
        assertNotNull(service.getAllById(1));
        assertEquals(2, service.getAllById(1).size());
    }

    @Test
    void testFindPaginated() {
    }

    @Test
    void testFindPaginatedFull() {
    }
}