package com.springboot.restapi.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.restapi.blog.entity.Role;
import com.springboot.restapi.blog.entity.User;
import com.springboot.restapi.blog.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// use co the su dung email or username de login
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
	               .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));

//		// Một GrantedAuthority là một quyền được ban cho principal. Các quyền đều có tiền tố là ROLE_, ví dụ như ROLE_ADMIN, ROLE_MEMBER
//		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		Set<Role> roles = user.getRoles();
//		for (Role role : roles) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
}


