package com.ga5000.api.blog.service.image;


import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.dto.image.ImageRequest;


import java.util.UUID;

public interface IImageService {
    void uploadImage(ImageRequest request, Post post);
    void deleteImage(UUID imageId);
}
