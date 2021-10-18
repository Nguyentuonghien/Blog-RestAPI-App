package com.springboot.restapi.blog.service.impl;

import com.springboot.restapi.blog.entity.Post;
import com.springboot.restapi.blog.exception.ResourceNotFoundException;
import com.springboot.restapi.blog.payload.PostDto;
import com.springboot.restapi.blog.payload.PostResponse;
import com.springboot.restapi.blog.repository.PostRepository;
import com.springboot.restapi.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNum, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Post> page = postRepository.findAll(pageable);

        // get content for page object and convert list post to list postDTO
        List<Post> listPosts = page.getContent();
        List<PostDto> content = listPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageSize(page.getSize());  // page hien tai
        postResponse.setPageNumber(page.getNumber()); // so luong ptu cua page hien tai
        postResponse.setTotalElements(page.getTotalElements()); // tong tat ca cac ptu
        postResponse.setTotalPages(page.getTotalPages());  // tong tat ca cac page
        postResponse.setLast(page.isLast());   // co phai page cuoi cung khong?

        return postResponse;
    }

    @Override
    public PostDto getById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    // convert DTO to entity for save to database
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    // convert entity to DTO
    private PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

}
