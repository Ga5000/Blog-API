package com.ga5000.api.blog.service.image;

import com.ga5000.api.blog.domain.image.Image;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.dto.image.ImageRequest;
import com.ga5000.api.blog.repository.image.ImageRepository;
import com.ga5000.api.blog.service.minioS3.MinioS3Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

import static com.ga5000.api.blog.utils.RepositoryUtils.findByIdOrThrow;


@Service
public class ImageService implements IImageService {
    private final String IMAGE_NOT_FOUND_MESSAGE = "Image not found";
    private final MinioS3Service minioS3Service;
    private final ImageRepository imageRepository;


    public ImageService(MinioS3Service minioS3Service, ImageRepository imageRepository) {
        this.minioS3Service = minioS3Service;
        this.imageRepository = imageRepository;
    }


    @Transactional
    @Override
    public void uploadImage(ImageRequest request, Post post) {
        String objectId = minioS3Service.uploadImage(request.file());
        saveImage(new Image(objectId, post));
    }

    @Transactional
    @Override
    public void deleteImage(UUID imageId) {
        var existingImage = findById(imageId);
        minioS3Service.deleteImage(existingImage.getObjectId());
        imageRepository.delete(existingImage);
        imageRepository.flush();
    }

    private void saveImage(Image image){
        imageRepository.save(image);
    }

    private Image findById(UUID imageId) throws EntityNotFoundException{
        return  findByIdOrThrow(imageId, imageRepository, () -> new EntityNotFoundException(IMAGE_NOT_FOUND_MESSAGE));
    }
}
