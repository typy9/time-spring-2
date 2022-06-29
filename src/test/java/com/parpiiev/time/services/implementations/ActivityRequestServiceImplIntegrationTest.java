package com.parpiiev.time.services.implementations;

import com.parpiiev.time.exceptions.request.InvalidRequestException;
import com.parpiiev.time.model.*;
import com.parpiiev.time.repository.ActivityRepository;
import com.parpiiev.time.repository.ActivityRequestRepository;
import com.parpiiev.time.repository.CategoryRepository;
import com.parpiiev.time.repository.UserRepository;
import com.parpiiev.time.services.interfaces.ActivityRequestService;
import com.parpiiev.time.services.interfaces.ActivityService;
import com.parpiiev.time.utils.dto.ActivityDTO;
import com.parpiiev.time.utils.dto.ActivityRequestDTO;
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
class ActivityRequestServiceImplIntegrationTest {

    @Autowired
    private ActivityRequestRepository repository;
    @Autowired
    private ActivityRequestService<ActivityRequestDTO> service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        User user = new User("name", "log", "pass", "user");
        userRepository.save(user);

        Category category = new Category("management");
        categoryRepository.save(category);

        Activity activity = new Activity("general", category);
        Activity activity1 = new Activity("test", category);
        activityRepository.save(activity);
        activityRepository.save(activity1);

        ActivityRequest activityRequest =
                new ActivityRequest(1, user, activity, Status.CREATED);
        ActivityRequest activityRequest1 =
                new ActivityRequest(2, user, activity1, Status.APPROVED);

        List<ActivityRequest> activityRequestList = new ArrayList<>();
        activityRequestList.add(activityRequest);
        activityRequestList.add(activityRequest1);

        repository.saveAll(activityRequestList);
    }


    @Test
    void testGetById() {
        Optional<ActivityRequestDTO> request = service.getById(1);
        assertTrue(request.isPresent());
        assertEquals(Status.CREATED, request.get().getStatus());
    }

    @Test
    void testGetAll() {
        assertFalse(service.getAll().isEmpty());
        assertEquals(2, service.getAll().size());
    }

    @Test
    void save() {
        Category category = new Category("second");
        categoryRepository.save(category);
        Activity activity = new Activity("additional", category);
        activityRepository.save(activity);

        ActivityRequestDTO dto = new ActivityRequestDTO(3, 1,3, Status.CREATED);
        assertTrue(service.save(dto));
        assertEquals(3, service.getAll().size());
    }

    @Test
    void update() {
        ActivityRequestDTO dto = new ActivityRequestDTO(2, 1,2, Status.DECLINED);
        service.update(dto);
        assertTrue(service.getById(2).isPresent());
        assertEquals(Status.DECLINED, service.getById(2).get().getStatus());
    }

    @Test
    void testDelete() {
        assertEquals(2, service.getAll().size());
        service.delete(2);
        assertEquals(1, service.getAll().size());
    }

    @Test
    void testGetAllByUserId() {
        assertEquals(2, service.getAllByUserId(1).size());
        assertTrue( service.getAllByUserId(1).get(0).isPresent());
    }
}