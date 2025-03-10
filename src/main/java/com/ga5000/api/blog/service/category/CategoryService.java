package com.ga5000.api.blog.service.category;

import com.ga5000.api.blog.domain.category.Category;
import com.ga5000.api.blog.dto.category.CategoryRequest;
import com.ga5000.api.blog.dto.category.CategoryResponse;
import com.ga5000.api.blog.dto.category.UpdateCategoryRequest;
import com.ga5000.api.blog.exception.entity.EntityAlreadyExistsException;
import com.ga5000.api.blog.repository.category.CategoryRepository;
import com.ga5000.api.blog.utils.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ga5000.api.blog.utils.RepositoryUtils.findByIdOrThrow;

@Service
public class CategoryService implements ICategoryService{
    private final String CATEGORY_NOT_FOUND_MESSAGE = "Category Not Found";
    private final CategoryRepository categoryRepository;
    private final DtoMapper dtoMapper;


    public CategoryService(CategoryRepository categoryRepository, DtoMapper dtoMapper) {
        this.categoryRepository = categoryRepository;
        this.dtoMapper = dtoMapper;
    }


    @Transactional
    @Override
    public void createCategory(CategoryRequest request) {
        validateCategoryCreation(request.name());
        saveCategory(new Category(request.name()));
    }

    @Override
    public void createCategories(List<CategoryRequest> requests) {
        requests.forEach(this::createCategory);
    }

    @Transactional
    @Override
    public void updateCategory(UpdateCategoryRequest request) {
        var existingCategory = findById(request.categoryId());
        validateCategoryCreation(request.name());
        existingCategory.setName(request.name());
        saveCategory(existingCategory);
    }

    @Transactional
    @Override
    public void deleteCategory(UUID categoryId) {
        var existingCategory = findById(categoryId);
        categoryRepository.delete(existingCategory);
    }

    @Override
    public Set<CategoryResponse> getCategories() {
        return categoryRepository.findAll(Sort.by("name"))
                .stream().map(dtoMapper::toCategoryResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<CategoryResponse> getPostCategories(UUID postId) {
        return categoryRepository.findPostCategories(postId)
                .stream().map(dtoMapper::toCategoryResponse).collect(Collectors.toSet());
    }

    private void validateCategoryCreation(String name) {
        categoryRepository.findByNameIgnoreCase(name)
                .ifPresent(category -> {
                    throw new EntityAlreadyExistsException("Category with name '" + name + "' already exists.");
                });
    }

    private void saveCategory(Category category){
        categoryRepository.save(category);
    }

    private Category findById(UUID categoryId){
        return findByIdOrThrow(
                categoryId,
                categoryRepository,
                () -> new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE));
    }
}
