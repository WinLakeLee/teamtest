package com.example.teamtest.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.teamtest.domain.Grade;
import com.example.teamtest.domain.OAuthType;
import com.example.teamtest.domain.entity.UserEntity;
import com.google.gson.Gson;

@Service
public class KakaoLoginService {
	
	@Value("${kakao_client_id}")
	private String clientId;
	
	@Autowired
	private TotalService totalService;
	
	public String getAccessToken(String code) {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("redirect_uri", "http://localhost:8888/oauth/kakao");
		body.add("code", code);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, header); 
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
					  "https://kauth.kakao.com/oauth/token"
					, HttpMethod.POST
					, requestEntity
					, String.class
				);
		String jsonData = responseEntity.getBody();
		Gson gson = new Gson();
		Map<?, ?> data = gson.fromJson(jsonData, Map.class);
		return (String)data.get("access_token");
	}
	
	public UserEntity getUserInfo(String accessToken) {
		
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization"
				, "Bearer " + accessToken
		);
		header.add("Content-Type"
				,"application/x-www-form-urlencoded;charset=utf-8"
		);
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = 
				new HttpEntity<>(header);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(
					"https://kapi.kakao.com/v2/user/me",
					HttpMethod.POST,
					requestEntity,
					String.class
				);
		String jsonData = responseEntity.getBody();
		Gson gson = new Gson();
		Map<?, ?> data = gson.fromJson(jsonData, Map.class);
		Integer id =  Double.valueOf((Double)data.get("id")).intValue();
		String username = (String)((Map<?, ?>)data.get("properties")).get("nickname");
		String email = (String)((Map<?, ?>)data.get("kakao_account")).get("email");
		UserEntity user = UserEntity.builder()
				.username(id.toString())
				.password(id.toString())
				.nickname(username)
				.email(email)
				.count(0)
				.point(0)
				.grade(Grade.BRONZE)
				.oAuthType(OAuthType.KAKAO)
				.build();
		return user;
	}
}
