package com.ga5000.api.blog.controller.comment;

import com.ga5000.api.blog.controller.user.UserController;
import com.ga5000.api.blog.dto.comment.CommentRequest;
import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.service.comment.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

import static com.ga5000.api.blog.utils.RequestMessageUtils.SUCCESS_KEY;


@RestController
@RequestMapping("/api/comments")
public class CommentController implements ICommentController{
    private final CommentService commentService;
    private final PagedResourcesAssembler<CommentResponse> pagedResourcesAssembler;
    public CommentController(CommentService commentService, PagedResourcesAssembler<CommentResponse> pagedResourcesAssembler) {
        this.commentService = commentService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @DeleteMapping("{commentId}/delete")
    @Override
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable("commentId") UUID commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(SUCCESS_KEY, "Comment deleted successfully"));
    }

    @PatchMapping("{commentId}/update/content")
    @Override
    public ResponseEntity<Map<String, String>> updateCommentContent(@PathVariable("commentId") UUID commentId,
                                                                    @RequestBody @Valid CommentRequest request) {
        commentService.updateCommentContent(commentId, request);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(SUCCESS_KEY, "Comment updated successfully"));
    }

    @PostMapping("{commentId}/reply")
    @Override
    public ResponseEntity<Map<String, String>> replyToComment(@PathVariable("commentId") UUID commentId,
                                                              @RequestBody @Valid CommentRequest request) {
        commentService.replyComment(commentId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(SUCCESS_KEY, "Comment replied successfully"));
    }

    @PostMapping("/{commentId}/likeOrDislike")
    @Override
    public ResponseEntity<Void> likeOrDislikeComment(@PathVariable("commentId") UUID commentId, @RequestParam boolean likeOrDislike) {
        commentService.likeOrDislikeComment(commentId, likeOrDislike);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{commentId}/replies")
    @Override
    public ResponseEntity<PagedModel<EntityModel<CommentResponse>>> getCommentReplies(@PathVariable("commentId") UUID commentId,
                                                                                      @PageableDefault(size = 20) Pageable pageable) {
        Page<CommentResponse> replies = commentService.getCommentReplies(commentId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedResourcesAssembler.toModel(replies, reply -> EntityModel.of(reply,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                                .getUserInfo(reply.author().username()))
                                .withSelfRel())));
    }
}
