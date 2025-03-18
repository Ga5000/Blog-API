package com.ga5000.api.blog.service.minioS3;

import com.ga5000.api.blog.middleware.exception.image.UploadImageException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class MinioS3Service {

    @Value(value = "${minio.bucket.name}")
    private String bucketName;

    @Value(value = "${minio.url}")
    private String url;

    private final MinioClient minioClient;
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of("image/png", "image/jpeg");

    public MinioS3Service(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadImage(MultipartFile file) throws UploadImageException{
        validateFileType(file);

        try (var inputStream = file.getInputStream()) {
            String objectId = generateObjectId(file);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectId)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return objectId;
        } catch (Exception e) {
            throw new UploadImageException("Failed to upload image");
        }
    }

    public void deleteImage(String objectId) throws UploadImageException {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectId)
                            .build()
            );
        } catch (Exception e) {
            throw new UploadImageException("Failed to delete image: " + objectId);
        }
    }

    public String getUrl(String objectId){
        return String.format("%s/%s/%s", url, bucketName, objectId);
    }

    private void validateFileType(MultipartFile file) throws UploadImageException {
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new UploadImageException("Invalid file type. Only PNG and JPEG are allowed.");
        }
    }

    private String generateObjectId(MultipartFile file) {
        return UUID.randomUUID() + "-" + file.getOriginalFilename() + "-" + LocalDate.now();
    }
}