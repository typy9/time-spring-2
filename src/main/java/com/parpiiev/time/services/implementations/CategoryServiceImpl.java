package com.parpiiev.time.services.implementations;

import com.parpiiev.time.exceptions.category.CategoryAlreadyExistsException;
import com.parpiiev.time.model.Category;
import com.parpiiev.time.exceptions.category.InvalidCategoryException;
import com.parpiiev.time.repository.CategoryRepository;
import com.parpiiev.time.services.interfaces.CategoryService;
import com.parpiiev.time.utils.dto.CategoryDTO;
import com.parpiiev.time.utils.dto.UserDTO;
import com.parpiiev.time.utils.dto.mappers.DtoMapper;
import com.parpiiev.time.utils.validators.PatternMatcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Categories table
 */
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService<CategoryDTO> {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final DtoMapper<CategoryDTO, Category> dtoMapper;
    private final CategoryRepository categoryRepository;


    @Override
    public Optional<CategoryDTO> getById(int id) {
        if (id <= 0) {
            return Optional.empty();
        }
        return categoryRepository.findById(id).map(dtoMapper::mapToDto);
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(CategoryDTO categoryDto) {
        boolean flag;
        if (categoryDto.getName() == null) {
            log.error("Invalid input data");
            throw new InvalidCategoryException("Category input data is not valid");
        }

        Category category = getCategory(categoryDto);

        if (getByName(categoryDto.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException();
        } else {
            flag = true;
            categoryRepository.save(category);
        }
        return flag;
    }

    @Override
    public void updateCategoryNameById(int categoryId, String name) {
        if (categoryId <= 0 || name == null || name.isBlank()) {
            throw new InvalidCategoryException();
        } else {
            categoryRepository.updateCategoryNameById(categoryId, name);
        }
    }

    /**
     * @param categoryDto to be mapped to category object
     * @return category object
     */
    private Category getCategory(CategoryDTO categoryDto) {
        return dtoMapper.mapFromDto(categoryDto);
    }


    @Override
    public boolean delete(int id) {
        if (id <= 0) {return false;}
//        return categoryDao.delete(id);
        categoryRepository.deleteById(id);
        return true;
    }


    /**
     * Find all Category objects with pagination
     * @param pageable
     * @return
     */
    @Override
    public Page<CategoryDTO> findPaginated(Pageable pageable) {
        log.info("Start CategoryServiceImpl findPaginated method");

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Pageable paging = PageRequest.of(currentPage, pageSize);
        Page<CategoryDTO> pagedResult = categoryRepository.findAll(paging).map(dtoMapper::mapToDto);

        log.debug("pagedResult : {}",pagedResult);
        log.debug("Content : {}",pagedResult.getContent());

        return pagedResult;
    }

    @Override
    public Optional<CategoryDTO> getByName(String name) {

        if (name == null) {
            return Optional.empty();
        }
        return categoryRepository.findByName(name).map(dtoMapper::mapToDto);
    }

}
