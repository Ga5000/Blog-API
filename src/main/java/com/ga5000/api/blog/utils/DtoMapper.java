package com.ga5000.api.blog.utils;

import com.ga5000.api.blog.domain.category.Category;
import com.ga5000.api.blog.domain.image.Image;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.dto.category.CategoryResponse;
import com.ga5000.api.blog.dto.image.ImageResponse;
import com.ga5000.api.blog.service.minioS3.MinioS3Service;
import org.springframework.stereotype.Component;


@Component
public class DtoMapper {
    private final MinioS3Service minioS3Service;

    public DtoMapper(MinioS3Service minioS3Service) {
        this.minioS3Service = minioS3Service;
    }

    public ImageResponse toImageResponse(Image image) {
        return new ImageResponse(
                image.getImageId(),
                minioS3Service.getUrl(image.getObjectId())
        );
    }

    public CategoryResponse toCategoryResponse(Category category){
        return new CategoryResponse(
                category.getCategoryId(),
                category.getName()
        );
    }



}
