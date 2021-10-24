package com.springboot.restapi.blog.service;

import com.springboot.restapi.blog.payload.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CommentService {

    public CommentDto createComment(CommentDto commentDto, long postId);

    public List<CommentDto> getCommentsByPostId(long postId);

    public CommentDto getCommentById(long postId, long id);

    public CommentDto updateComment(long postId, long id, CommentDto commentDto);

    public void deleteComment(long postId, long id);

}
