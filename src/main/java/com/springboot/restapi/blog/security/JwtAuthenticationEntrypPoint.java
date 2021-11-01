package com.springboot.restapi.blog.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthenticationEntrypPoint implements AuthenticationEntryPoint {

	/**
	 * phương thức commence() được gọi bất cứ khi nào một ngoại lệ được đưa ra do user chưa được xác thực 
	 * đang cố gắng truy cập vào một tài nguyên yêu cầu xác thực
	 */	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
			             throws IOException, ServletException {
		// HttpServletResponse.SC_UNAUTHORIZED chính là 401, cho biết yêu cầu cần xác thực
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		
	}

}





