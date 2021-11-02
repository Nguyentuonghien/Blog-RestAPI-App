package com.springboot.restapi.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.restapi.blog.security.CustomUserDetailsService;
import com.springboot.restapi.blog.security.JwtAuthenticationEntrypPoint;
import com.springboot.restapi.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtAuthenticationEntrypPoint authenticationEntrypPoint;
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .exceptionHandling().authenticationEntryPoint(authenticationEntrypPoint)
		    .and()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // đảm bảo rằng ta sử dụng stateless session, session sẽ không được sử dụng để lưu trữ trạng thái của người dùng.
		    .and()
		    .authorizeRequests()
		    .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
		    .antMatchers("/api/v1/auth/**").permitAll()
		    .anyRequest().authenticated();
		
		// add filter để validate các tokens với mọi request
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails admin = User.builder().username("admin").password(passwordEncoder()
//				.encode("admin")).roles("ADMIN").build();
//		UserDetails trang = User.builder().username("trang").password(passwordEncoder()
//				.encode("password")).roles("USER").build();
//		return new InMemoryUserDetailsManager(admin, trang);
//	}

	
}




