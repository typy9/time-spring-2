package com.parpiiev.time.services.implementations;

import com.parpiiev.time.model.Category;
import com.parpiiev.time.repository.CategoryRepository;
import com.parpiiev.time.services.interfaces.CategoryService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CategoryServiceImplIntegrationTest {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private CategoryService<CategoryDTO> service;


    @BeforeEach
    void setUp() {
        Category category = new Category("general");
        Category category1 = new Category( "administration");
        Category category2 = new Category( "management");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category1);
        categoryList.add(category2);
        repository.saveAll(categoryList);
    }

    @Test
    void testGetById() {
        Optional<CategoryDTO> category = service.getById(1);
        assertTrue(category.isPresent());
        assertEquals("general", category.get().getName());
    }

    @Test
    void testGetAll() {
        assertFalse(service.getAll().isEmpty());
        assertEquals(3, service.getAll().size());
    }

    @Test
    void testSave() {
        CategoryDTO dto = new CategoryDTO(4, "test");
        service.save(dto);
        assertEquals(4, service.getAll().size());
        assertEquals("test", service.getById(4).get().getName());
    }

    @Test
    void testUpdateCategoryNameById() {

        service.updateCategoryNameById(3, "updated");
        assertNotEquals("test", service.getById(3).get().getName());
        assertEquals("updated", service.getById(3).get().getName());

    }

    @Test
    void testDelete() {
        assertEquals(3, service.getAll().size());
        service.delete(3);
        assertEquals(2, service.getAll().size());
    }
}