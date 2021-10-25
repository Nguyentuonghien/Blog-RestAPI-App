package com.springboot.restapi.blog.service;

import java.util.List;

import com.springboot.restapi.blog.payload.CommentDto;

public interface CommentService {

    public CommentDto createComment(CommentDto commentDto, long postId);

    public List<CommentDto> getCommentsByPostId(long postId);

    public CommentDto getCommentById(long postId, long id);

    public CommentDto updateComment(long postId, long id, CommentDto commentDto);

    public void deleteComment(long postId, long id);

}
