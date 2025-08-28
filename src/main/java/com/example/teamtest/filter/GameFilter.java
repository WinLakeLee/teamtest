package com.example.teamtest.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.CannotProceedException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.teamtest.jwt.JwtService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GameFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	
	private final int DAILY_LIMIT = 3;
	
	private final Map<String, Integer> attempts = new HashMap<>();
	
	private static final String PREFIX = "game";
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getRequestURI().startsWith(PREFIX)) {
			String jwt = jwtService.resolveToken(request);
			String username = jwtService.getUsername(jwt);
			if(StringUtils.isNotEmpty(username)) {
				if(attempts.containsKey(username)) {
					if(attempts.get(username) >= DAILY_LIMIT) 
						new CannotProceedException();
					Integer currentAttempts = attempts.get(username) + 1;
					attempts.put(username, currentAttempts);
				} else {
					attempts.put(username, 1);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
