package com.parpiiev.time.utils.dto.mappers;

import com.parpiiev.time.model.Category;
import com.parpiiev.time.utils.dto.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements DtoMapper<CategoryDTO, Category>{
    @Override
    public CategoryDTO mapToDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategory_id(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    @Override
    public Category mapFromDto(CategoryDTO categoryDTO) {
        Category category= new Category();
        category.setName(categoryDTO.getName());
        return category;
    }
}
