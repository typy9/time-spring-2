package com.parpiiev.time.services.implementations;

import com.parpiiev.time.model.Activity;
import com.parpiiev.time.model.Category;
import com.parpiiev.time.repository.ActivityRepository;
import com.parpiiev.time.repository.CategoryRepository;
import com.parpiiev.time.services.interfaces.ActivityRequestService;
import com.parpiiev.time.services.interfaces.ActivityService;
import com.parpiiev.time.utils.dto.ActivityDTO;
import com.parpiiev.time.utils.dto.CategoryDTO;
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
class ActivityServiceImplIntegrationTest {

    @Autowired
    private ActivityRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ActivityService<ActivityDTO> service;

    @BeforeEach
    void setUp() {
        Category category = new Category("test");
        categoryRepository.save(category);
        Activity activity = new Activity("general", category);
        Activity activity1 = new Activity("administration", category);
        Activity activity2 = new Activity("management", category);
        List<Activity> activityList = new ArrayList<>();
        activityList.add(activity);
        activityList.add(activity1);
        activityList.add(activity2);
        repository.saveAll(activityList);
    }

    @Test
    void getById() {
        Optional<ActivityDTO> activity = service.getById(2);
        assertTrue(activity.isPresent());
        assertEquals("administration", activity.get().getName());
    }

    @Test
    void testGetAll() {
        assertFalse(service.getAll().isEmpty());
        assertEquals(3, service.getAll().size());
    }

    @Test
    void testGetByName() {

        assertTrue(service.getByName("updated").isEmpty());
        assertFalse(service.getByName("management").isEmpty());
    }

    @Test
    void testSave() {
        ActivityDTO dto = new ActivityDTO(4, "test", 1);
        service.save(dto);
        assertEquals(4, service.getAll().size());
        assertEquals("test", service.getById(4).get().getName());
    }

    @Test
    void testUpdate() {
        ActivityDTO dto = new ActivityDTO(1, "updated", 1);
        service.update(dto);
        assertNotEquals("test", service.getById(1).get().getName());
        assertEquals("updated", service.getById(1).get().getName());
    }

    @Test
    void testDelete() {
        assertEquals(3, service.getAll().size());
        service.delete(3);
        assertEquals(2, service.getAll().size());
    }

    @Test
    void findPaginatedSorted() {
    }

    @Test
    void findPaginated() {
    }

    @Test
    void testGetActivityByCategory_Id() {
        assertNull( service.getActivityByCategory_Id(-1));
        assertTrue( service.getActivityByCategory_Id(2).isEmpty());
        assertEquals(3, service.getActivityByCategory_Id(1).size());
    }

    @Test
    void findPaginatedSortedFiltered() {
    }
}