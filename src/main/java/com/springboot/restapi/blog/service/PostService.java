package com.springboot.restapi.blog.service;

import com.springboot.restapi.blog.payload.PostDto;
import javafx.geometry.Pos;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    public List<PostDto> getAllPosts();

    public PostDto getById(long id);

    public PostDto updatePost(PostDto postDto, long id);

    public void deletePost(long id);

}
