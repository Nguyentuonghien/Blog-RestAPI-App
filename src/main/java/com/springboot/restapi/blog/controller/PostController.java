package com.springboot.restapi.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restapi.blog.payload.PostDto;
import com.springboot.restapi.blog.payload.PostResponse;
import com.springboot.restapi.blog.service.PostService;
import com.springboot.restapi.blog.utils.AppConstants;

@RestController
@RequestMapping("")
public class PostController {

    @Autowired
    private PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/posts")
    public PostResponse getAllPosts(
               @RequestParam(value = "pageNum", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
               @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
               @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
               @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return postService.getAllPosts(pageNum, pageSize, sortBy, sortDir);
    }

    @GetMapping(value = "/api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
        // PostDto postDto = postService.getById(id);
        // return new ResponseEntity<>(postDto, HttpStatus.OK);
        return ResponseEntity.ok(postService.getById(id));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") long id) {
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }

    
    
//    @GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=2")
//    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable("id") long id) {
//    	PostDto postDto = postService.getById(id);
//    	PostDtoV2 postDtoV2 = new PostDtoV2();
//    	postDtoV2.setId(postDto.getId());
//    	postDtoV2.setContent(postDto.getContent());
//    	postDtoV2.setDescription(postDto.getDescription());
//    	postDtoV2.setTitle(postDto.getTitle());
//    	postDtoV2.setComments(postDto.getComments());
//    	List<String> tags = Arrays.asList("Java", "SpringBoot", "AWS");
//    	postDtoV2.setTags(tags);
//    	
//    	return ResponseEntity.ok(postDtoV2);
//    }

}
