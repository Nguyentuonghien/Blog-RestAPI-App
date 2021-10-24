package com.springboot.restapi.blog.payload;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

@Data
public class CommentDto {

    private Long id;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

}

