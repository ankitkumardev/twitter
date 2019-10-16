package com.fareye.twitter.jwt;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
	
	
	private static final long serialVersionUID = 6989051222406720106L;
	private String token;

	public JwtAuthenticationToken(Object principal, Object credentials,String token) {
		super(principal, credentials);
		this.setToken(token);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
