package com.example.teamtest.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.teamtest.domain.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 3666766485740613365L;
	
	private UserEntity userEntity;
	
	private Map<String, Object> attributes;
	
	@Override
	public Map<String, Object> getAttributes() {
			attributes.put("Grade", userEntity.getGrade().toString());
			return attributes;
	}
	
	@Override
	public String getName() {
		return userEntity.getUsername();
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singletonList(
				new SimpleGrantedAuthority(
						userEntity.getGrade().toString()
						)
				);
	}
	
	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}
	
	@Override
	public String getUsername() {
		return userEntity.getUsername();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public UserDetailsImpl(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	public UserDetailsImpl(UserEntity entity, Map<String, Object> attributes) {
		this.userEntity = entity;
		this.attributes = attributes;
	}
	
}
