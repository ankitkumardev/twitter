package com.fareye.twitter.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fareye.twitter.dao.MainDao;
import com.fareye.twitter.entity.UserDetail;

@Component
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	@Autowired
	private JwtValidator validator;
	@Autowired
	private MainDao mainDao;
	
	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication)||UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if(!(authentication.getCredentials()==null)&&!userDetails.getPassword().equals(authentication.getCredentials())) {
			throw new AuthenticationException("Wrong Password"){
				private static final long serialVersionUID = 1L;	
			};
		}
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
		String token = authenticationToken.getToken();
		if(token!=null) {
		JwtRequest jwtUser = validator.validate(token);
		if(jwtUser == null) {
			throw new RuntimeException("Jwt token is incorrect");
		}
//		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//				.commaSeparatedStringToAuthorityList(jwtUser.getRole());
		
		return new MyUserDetails(jwtUser.getUsername(),0,token,jwtUser.getPassword());
		}
		else {
			Optional<UserDetail> user = mainDao.getUserByName(authentication.getPrincipal().toString());
			UserDetail user1 = user.get();
			return new MyUserDetails(user1.getName(), user1.getId(), null,user1.getPassword());
		}
	}
}
