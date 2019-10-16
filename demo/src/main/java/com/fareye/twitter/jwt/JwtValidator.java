package com.fareye.twitter.jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
@Component
public class JwtValidator {
	
	public JwtRequest validate(String token) {
		JwtRequest jwtUser = null;
		try {
		Claims claims = Jwts.parser()
				.setSigningKey("ankit")
				.parseClaimsJws(token)
				.getBody();
		jwtUser = new JwtRequest();
		jwtUser.setUsername(claims.getSubject());
		jwtUser.setPassword(claims.get("userId").toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jwtUser;
	}

}
