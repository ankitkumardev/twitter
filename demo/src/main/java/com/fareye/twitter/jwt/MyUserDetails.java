package com.fareye.twitter.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 5818828186522597097L;
	private String userName;
	private String password;
	private long id;
	private String token;
//	private List<GrantedAuthority> grantedAuthorities;

	public MyUserDetails(String userName, long id, String token, /*List<GrantedAuthority> grantedAuthorities,*/ String password) {
		this.userName = userName;
		this.id = id;
		this.token = token;
//		this.grantedAuthorities = grantedAuthorities;
		this.password = password;
		
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
//	public List<GrantedAuthority> getGrantedAuthorities() {
//		return grantedAuthorities;
//	}
	public String getUserName() {
		return userName;
	}
	public long getId() {
		return id;
	}
	public String getToken() {
		return token;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

}
