package com.parpiiev.time.controllers;

import com.parpiiev.time.exceptions.category.CategoryAlreadyExistsException;
import com.parpiiev.time.exceptions.category.InvalidCategoryException;
import com.parpiiev.time.services.interfaces.CategoryService;
import com.parpiiev.time.utils.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parpiiev.time.controllers.Paths.*;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService<CategoryDTO> categoryService;


    @GetMapping(ALL_CATEGORIES_PAGE)
    public String listCategories(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        log.debug("Start CategoryController GET listCategories");

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(2);

        Page<CategoryDTO> categoryPage = categoryService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize));
        List<CategoryDTO> categories = categoryPage.getContent();
        model.addAttribute("categoryPage", categoryPage);

        int totalPages = categoryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("categoryDTO", new CategoryDTO());
        model.addAttribute("categories", categories);

        return ALL_CATEGORIES_FILE;
    }


    @GetMapping(CATEGORY_DELETE_PAGE)
    public String deleteCategory(@PathVariable("id") int id, Model model) {

        log.debug("Start CategoryController GET deleteCategory");

        Optional<CategoryDTO> category = categoryService.getById(id);

        if(category.isPresent()) {
            categoryService.delete(id);
            List<CategoryDTO> categories = categoryService.getAll();
            model.addAttribute("categories", categories);
        }
        return BACK_TO_ALL_CATEGORIES_PAGE;
    }

    @GetMapping(CATEGORY_UPDATE_PAGE)
    public String categoryUpdatePage(@PathVariable("id") int id, Model model) {

        log.debug("Start CategoryController GET categoryUpdatePage");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategory_id(id);
        model.addAttribute("categoryDTO", categoryDTO);
        return CATEGORY_UPDATE_FILE;
    }

    @PostMapping(CATEGORY_UPDATE_PAGE)
    public String updateCategory(@PathVariable("id") int id, @Valid CategoryDTO categoryDTO,
                             BindingResult bindingResult, Model model) {

        log.info("Start CategoryController POST updateCategory");
        log.debug("ID is : {}", id);
        log.debug("categoryDTO retrieved from the form is {}", categoryDTO);

        if (bindingResult.hasErrors()) {
            return BACK_TO_ALL_CATEGORIES_PAGE;
        }

        categoryDTO.setCategory_id(id);
        try {
            categoryService.updateCategoryNameById(id, categoryDTO.getName());
        } catch (InvalidCategoryException e) {
            log.error("InvalidCategoryException", e);
            return BACK_TO_ALL_CATEGORIES_PAGE;
        }
        List<CategoryDTO> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return BACK_TO_ALL_CATEGORIES_PAGE;
    }

    @PostMapping(ALL_CATEGORIES_PAGE)
    public String addCategory(Model model, @ModelAttribute @Valid CategoryDTO categoryDTO,
                              BindingResult bindingResult,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size){

        log.debug("Start CategoryController GET addCategory");

        if (bindingResult.hasErrors()) {
            List<CategoryDTO> categories = categoryService.getAll();
            model.addAttribute("categories", categories);
            return BACK_TO_ALL_CATEGORIES_PAGE;
        }

        String name = categoryDTO.getName();

        if (name == null) {
            model.addAttribute("isInvalidData", true);
            return BACK_TO_ALL_CATEGORIES_PAGE;
        }

        boolean isSaved;

        try {
            isSaved = categoryService.save(categoryDTO);
        } catch (CategoryAlreadyExistsException e) {
            model.addAttribute("isCategoryExists", true);
            log.warn("Such category already exists", e);
            return BACK_TO_ALL_CATEGORIES_PAGE;
        }

        if (isSaved) {
            model.addAttribute("categoryRegistered", true);
        }
        List<CategoryDTO> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return BACK_TO_ALL_CATEGORIES_PAGE;
    }
}
