package com.springboot.restapi.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * mục đích của JwtAuthenticationFilter kiểm tra tất cả các request truyền lên server có hợp lệ hay không bằng việc kiểm tra token trên header,
 * đối với bất kỳ yêu cầu nào đến, lớp Filter này sẽ được thực thi, nó kiểm tra xem yêu cầu có JWT token hợp lệ hay không? 
 * nếu nó có JWT token hợp lệ thì nó sẽ đặt xác thực trong context để chỉ định rằng người dùng hiện tại được xác thực.
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			                        throws ServletException, IOException {
		// lấy JWT(token) từ http request
		String token = getJWTFromRequest(request);
		
		// validate token và lấy ra username từ token đó
		if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
			String usernameFromToken = tokenProvider.getUsernameFromJWTToken(token);
			// load user được liên kết với token
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(usernameFromToken);
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// sau khi setting Authentication trong context, ta chỉ định rằng user hiện tại đã được xác thực -> nó vượt qua Cấu hình Spring Security thành công.
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getJWTFromRequest(HttpServletRequest httpServletRequest) {
		// Kiểm tra xem header Authorization có chứa thông tin jwt không?
		// JWT Token có dạng "Bearer Token" -> remove từ "Bearer" và chỉ lấy từ "Token"
		String bearerToken = httpServletRequest.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());		
		}
		return null;
	}
	
	
}



