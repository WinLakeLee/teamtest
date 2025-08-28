package com.example.teamtest.jwt;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

	static final long EXPIRATIONTIME = 24 * 60 * 60 * 1000;
	
	static final String PREFIX = "Bearer ";
	
	static final SecretKey KEY = Jwts.SIG.HS256.key().build();
	
	static final String ROLES_CLAIM = "roles";
	
	public String createToken(String username, Collection<? extends GrantedAuthority> authorities) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + EXPIRATIONTIME);
		
		List<String> roles = (authorities == null) ? 
				List.of() 
				: 
				authorities.stream()
					.map(GrantedAuthority:: getAuthority)
					.toList();
		
		return Jwts.builder()
				.subject(username)
				.issuedAt(now)
				.expiration(exp)
				.signWith(KEY)
				.claim(ROLES_CLAIM, roles)
				.compact();
	}
	
	public String resolveToken(HttpServletRequest request) {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(header != null && header.startsWith(PREFIX))
			return header.substring(PREFIX.length()).trim();
		return null;
	}
	
	public boolean validate(String token) {
		if(token == null || token.isBlank())
			return false;
		try {
			Jwts.parser()
				.verifyWith(KEY)
				.clockSkewSeconds(30)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (UnsupportedJwtException e) {
			return false;
		} catch (JwtException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getUsername(String token) {
		return getClaim(token).getSubject();
	}
	
	public Claims getClaim(String token) {
		return Jwts.parser()
					.verifyWith(KEY)
					.build()
					.parseSignedClaims(token)
					.getPayload();
	}
	
	public List<? extends GrantedAuthority> getAuthorities(String token) {
		Claims claims = getClaim(token);
		Object raw = claims.get(ROLES_CLAIM);
		if(raw instanceof List<?> list) {
			return list.stream()
					.filter(String.class::isInstance)
					.map(String.class::cast)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		}
		return List.of();
	}
}
