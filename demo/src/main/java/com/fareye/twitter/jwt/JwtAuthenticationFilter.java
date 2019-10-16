package com.fareye.twitter.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

//import com.smile.jwtdemo.security.model.JwtAuthenticationToken;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public JwtAuthenticationFilter() {
		super("/rest/**");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		Optional<String> optional = Optional.ofNullable(request.getHeader("Authentication"));
		optional
			.orElseThrow(()-> new RuntimeException("No Jwt token is present in headers"));
		String authentication = optional.get();
		JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(null,null,authentication);
		return getAuthenticationManager().authenticate(authenticationToken);
	}

	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		chain.doFilter(request, response);
	}
}
