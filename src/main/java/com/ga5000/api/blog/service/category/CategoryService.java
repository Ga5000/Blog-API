package com.ga5000.api.blog.service.category;

import com.ga5000.api.blog.domain.category.Category;
import com.ga5000.api.blog.dto.category.CategoryRequest;
import com.ga5000.api.blog.dto.category.CategoryResponse;
import com.ga5000.api.blog.dto.category.UpdateCategoryRequest;
import com.ga5000.api.blog.middleware.exception.entity.EntityAlreadyExistsException;
import com.ga5000.api.blog.middleware.exception.entity.EntityNotFoundException;
import com.ga5000.api.blog.middleware.exception.entity.IllegalEntityStateException;
import com.ga5000.api.blog.repository.category.CategoryRepository;
import com.ga5000.api.blog.utils.DtoMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ga5000.api.blog.utils.RepositoryUtils.existsByIdOrThrow;
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


    @CacheEvict(value = "categories", key = "'allCategories'")
    @Transactional
    @Override
    public void createCategory(CategoryRequest request) {
        validateCategoryCreation(request.name());
        saveCategory(new Category(request.name()));
    }

    @CacheEvict(value = "categories", key = "'allCategories'")
    @Override
    public void createCategories(List<CategoryRequest> requests) {
        requests.forEach(this::createCategory);
    }

    @CacheEvict(value = "categories", key = "'allCategories'")
    @Transactional
    @Override
    public void updateCategory(UpdateCategoryRequest request) {
        var existingCategory = findById(request.categoryId());
        validateCategoryCreation(request.name());
        existingCategory.setName(request.name());
        saveCategory(existingCategory);
    }

    @CacheEvict(value = "categories", key = "'allCategories'")
    @Transactional
    @Override
    public void deleteCategory(UUID categoryId) throws IllegalEntityStateException{
       existsByIdOrThrow(categoryId, categoryRepository, () -> new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE));
        Long count = categoryRepository.countPostsWithOnlyCategory(categoryId);

        if (count != null && count > 0) {
            throw new IllegalEntityStateException("Cannot delete the only category associated with a post.");
        }
        categoryRepository.deleteById(categoryId);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "categories", key = "'allCategories'")
    @Override
    public Set<CategoryResponse> getCategories() {
        return categoryRepository.findAll(Sort.by("name"))
                .stream().map(dtoMapper::toCategoryResponse).collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public void associateCategoriesWithPost(List<UUID> categoryIds, UUID postId) {
        for (UUID categoryId : categoryIds) {
            findById(categoryId);
        }
        categoryRepository.associateCategoriesWithPost(postId, categoryIds);
    }


    private void validateCategoryCreation(String name) throws EntityAlreadyExistsException {
        categoryRepository.findByNameIgnoreCase(name)
                .ifPresent(category -> {
                    throw new EntityAlreadyExistsException("Category with name '" + name + "' already exists.");
                });
    }

    private void saveCategory(Category category){
        categoryRepository.save(category);
    }

    private Category findById(UUID categoryId) throws EntityNotFoundException{
        return findByIdOrThrow(
                categoryId,
                categoryRepository,
                () -> new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE));
    }
}
