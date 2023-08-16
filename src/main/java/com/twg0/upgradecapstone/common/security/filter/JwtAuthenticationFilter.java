package com.twg0.upgradecapstone.common.security.filter;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcook.capstone.common.security.auth.PrincipalDetails;
import com.timcook.capstone.user.dto.UserResponse;
import com.timcook.capstone.user.service.UserService;
import com.timcook.capstone.user.vo.UserLoginVO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final UserService userService;
	private final long VALID_TIME = 1000L * 60 * 60; // 1시간 
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	public JwtAuthenticationFilter(UserService userService) {
		this.userService = userService;
		
		setFilterProcessesUrl("/api/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		log.info("--JWT AUTHENTICATION FILTER--");
		
		try {
			
			UserLoginVO creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginVO.class);
			
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(), 
							creds.getPassword(),
							null
							));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String email = ((PrincipalDetails)authResult.getPrincipal()).getUsername();
		UserResponse userResponse = userService.findByEmail(email);
		
		String jwtToken = Jwts.builder()
								.setSubject(userResponse.getEmail())
								.setExpiration(new Date(System.currentTimeMillis() + VALID_TIME))
								.signWith(getSecretKey(), SignatureAlgorithm.HS256)
								.compact();
		
		response.addHeader("token", jwtToken);
		response.addHeader("username", userResponse.getUsername());
	}

	private Key getSecretKey() {
		byte[] KeyBytes = SECRET_KEY.getBytes();
		return Keys.hmacShaKeyFor(KeyBytes);
	}
	
}
