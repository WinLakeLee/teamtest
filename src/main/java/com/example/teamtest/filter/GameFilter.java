package com.example.teamtest.filter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.teamtest.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@Getter
@RequiredArgsConstructor
public class GameFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private static final String PREFIX = "game";
	private final int DAILY_LIMIT = 3;

	private final ConcurrentHashMap<String, Integer> attempts = new ConcurrentHashMap<>();

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		if (request.getRequestURI().startsWith(PREFIX)) {
			String jwt = jwtService.resolveToken(request);
			String username = jwtService.getUsername(jwt);
			if (StringUtils.hasText(username)) {
				int currentAttempts = attempts.compute(username, (key, count) -> (count == null) ? 1 : count + 1);
				if(currentAttempts > DAILY_LIMIT) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
