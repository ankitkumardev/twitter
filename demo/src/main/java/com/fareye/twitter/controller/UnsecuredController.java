package com.fareye.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fareye.twitter.dao.MainDao;
import com.fareye.twitter.entity.UserDetail;
import com.fareye.twitter.jwt.JwtAuthenticationToken;
import com.fareye.twitter.jwt.JwtRequest;
import com.fareye.twitter.jwt.JwtResponse;
//import com.fareye.twitter.jwt.JwtValidator;
import com.fareye.twitter.jwt.MyJwtTokenGenerator;

@RestController
@CrossOrigin
public class UnsecuredController {
	
	@Autowired
	private MyJwtTokenGenerator generator;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MainDao mainDao;
	
//	@Autowired
//	private JwtValidator validator;
	
	@PostMapping("/register")
	public UserDetail register(@RequestBody UserDetail user ) {
		mainDao.register(user);
		return user;
		
	}
	@GetMapping("/checkuser/{userName}")
	public boolean checkUserName(@PathVariable("userName") String user ) {
		return mainDao.checkUserName(user);		
	}
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//		Optional<UserDetail> userDetails = dao.getUserByName(authenticationRequest.getUsername());
		generator = new MyJwtTokenGenerator();
		final String token = generator.generate(authenticationRequest);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new JwtAuthenticationToken(username, password,null));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}


}
