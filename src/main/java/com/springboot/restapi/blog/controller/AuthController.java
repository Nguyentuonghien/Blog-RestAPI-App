
package com.springboot.restapi.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restapi.blog.entity.Role;
import com.springboot.restapi.blog.entity.User;
import com.springboot.restapi.blog.payload.LoginDto;
import com.springboot.restapi.blog.payload.SignUpDto;
import com.springboot.restapi.blog.repository.RoleRepository;
import com.springboot.restapi.blog.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signin")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		return new ResponseEntity<>("User signed-in successfully.", HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto) {
		// nếu username or email đã tồn tại trong DB -> báo lỗi
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<String>("Username is already taken!", HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<String>("Email is already taken!", HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setEmail(signUpDto.getEmail());
		user.setUsername(signUpDto.getUsername());
		user.setName(signUpDto.getName());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));
		userRepository.save(user);
		
		return new ResponseEntity<String>("User registered successfully!", HttpStatus.OK);
	}
	
}




