package com.springboot.restapi.blog.controller;

import com.springboot.restapi.blog.payload.CommentDto;
import com.springboot.restapi.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        CommentDto commentDTOresponse = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(commentDTOresponse, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPost(@PathVariable("postId") long postId) {
        List<CommentDto> commentDtos = commentService.getCommentsByPostId(postId);
        return commentDtos;
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getById(@PathVariable("postId") long postId, @PathVariable("id") long id) {
        CommentDto commentDto = commentService.getCommentById(postId, id);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,
                                                    @PathVariable("id") long id,
                                                    @Valid @RequestBody CommentDto commentDto) {
        CommentDto commentDtoResponse = commentService.updateComment(postId, id, commentDto);
        return new ResponseEntity<>(commentDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
                                                @PathVariable("id") long id) {
        commentService.deleteComment(postId, id);
        return new ResponseEntity<>("Comment deleted successfully.", HttpStatus.OK);
    }

}
