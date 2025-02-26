package com.ga5000.api.blog.service.category;

import com.ga5000.api.blog.domain.category.Category;
import com.ga5000.api.blog.dto.CategoryRequest;
import com.ga5000.api.blog.dto.CategoryResponse;
import com.ga5000.api.blog.exception.other.NoUpdateNeededException;
import com.ga5000.api.blog.exception.category.CategoryNotFoundException;
import com.ga5000.api.blog.repository.category.CategoryRepository;
import com.ga5000.api.blog.utils.DtoMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ga5000.api.blog.utils.RepositoryUtils.areFieldsEqual;
import static com.ga5000.api.blog.utils.RepositoryUtils.findByIdOrThrow;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final DtoMapper dtoMapper;

    public CategoryService(CategoryRepository categoryRepository, DtoMapper dtoMapper) {
        this.categoryRepository = categoryRepository;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        validateCategory(categoryRequest.name());
        saveCategory(new Category(categoryRequest.name()));
    }

    @Transactional
    @Override
    public void createCategories(List<CategoryRequest> categoryRequests) {
        List<Category> categoriesToCreate = new ArrayList<>();
        for (CategoryRequest request : categoryRequests) {
            validateCategory(request.name());
            categoriesToCreate.add(new Category(request.name()));
        }
        categoryRepository.saveAll(categoriesToCreate);
    }

    @Transactional
    @Override
    public void updateCategory(UUID categoryId, CategoryRequest categoryRequest) throws NoUpdateNeededException {
        Category existingCategory = findByIdOrThrow(categoryId, categoryRepository, CategoryNotFoundException::new);
        if (areFieldsEqual(existingCategory.getName(), categoryRequest.name())) {
            throw new NoUpdateNeededException();
        } else {
            validateCategory(categoryRequest.name());
            existingCategory.setName(categoryRequest.name());
            saveCategory(existingCategory);
        }
    }

    @Transactional
    @Override
    public void deleteCategory(UUID categoryId) {
        Category category = findByIdOrThrow(categoryId, categoryRepository, CategoryNotFoundException::new);
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll(Sort.by("name"))
                .stream()
                .map(dtoMapper::toCategoryResponse)
                .toList();
    }

    private void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    private void validateCategory(String name) throws CategoryNotFoundException {
        boolean exists = categoryRepository.existsByNameIgnoreCase(name);
        if (!exists) {
            throw new CategoryNotFoundException();
        }
    }
}
