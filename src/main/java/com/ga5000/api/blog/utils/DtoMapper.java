package com.ga5000.api.blog.utils;

import com.ga5000.api.blog.domain.category.Category;
import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.engagement.Engagement;
import com.ga5000.api.blog.domain.image.Image;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.dto.category.CategoryResponse;
import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.dto.image.ImageResponse;
import com.ga5000.api.blog.dto.post.PostResponse;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import com.ga5000.api.blog.dto.user.AuthorResponse;
import com.ga5000.api.blog.dto.user.UserInfoResponse;
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

    public AuthorResponse toAuthorResponse(User user) {
        return new AuthorResponse(
                user.getProfilePicture(),
                user.getUsername()
        );
    }

    public UserInfoResponse toUserInfoResponse(User user) {
        return new UserInfoResponse(
                user.getUsername(),
                user.getProfilePicture(),
                user.getCreatedAt()
        );
    }

    public PostResponse toPostResponse(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getTitle(),
                toImageResponse(post.getImage()),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                toAuthorResponse(post.getAuthor()),
                post.getPostEngagements().stream().mapToInt(Engagement::getLikeCount).sum(),
                post.getPostEngagements().stream().mapToInt(Engagement::getDislikeCount).sum()
        );
    }

    public PostSearchResponse toPostSearchResponse(Post post){
        return new PostSearchResponse(
                post.getPostId(),
                toImageResponse(post.getImage()),
                post.getTitle(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                toAuthorResponse(post.getAuthor())
        );
    }

    public CommentResponse toCommentResponse(Comment comment){
        return new CommentResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                toAuthorResponse(comment.getAuthor()),
                comment.getReplyCount(),
                comment.getCommentEngagements().stream().mapToInt(Engagement::getLikeCount).sum(),
                comment.getCommentEngagements().stream().mapToInt(Engagement::getDislikeCount).sum()

        );
    }



}
