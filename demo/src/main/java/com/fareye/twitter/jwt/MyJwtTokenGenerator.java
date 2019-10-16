package com.fareye.twitter.jwt;


import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class MyJwtTokenGenerator {

	public String generate(JwtRequest authenticationRequest) {
		Claims claims = Jwts.claims()
				.setSubject(authenticationRequest.getUsername());
		claims.put("userId", authenticationRequest.getPassword());
//		claims.put("role", jwtUser.getRole());
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, "ankit")
				.compact();
	}

}
