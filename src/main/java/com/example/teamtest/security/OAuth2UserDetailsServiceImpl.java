package com.example.teamtest.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.teamtest.domain.DTO.UserDTO;
import com.example.teamtest.domain.entity.UserEntity;
import com.example.teamtest.service.TotalService;
import com.example.teamtest.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuth2UserDetailsServiceImpl extends DefaultOAuth2UserService{
	
	private final UserService userService;
	private final TotalService totalService;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		Map<String, String> map = new HashMap<>();
		map.put("id", oAuth2User.getAttribute("sub"));
		map.put("email", oAuth2User.getAttribute("sub"));
		map.put("nickname", oAuth2User.getAttribute("name"));
		
		UserDTO findUser = new UserDTO();
			findUser.setUsername(map.get("id"));
			findUser.setEmail(map.get("email"));
			findUser.setNickname(map.get("name"));
			findUser.setPassword(totalService.generatePw());
		UserEntity entity = userService.insert(findUser);
		
		return new UserDetailsImpl(entity, oAuth2User.getAttributes());
	
	}

}
