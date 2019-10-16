package com.fareye.twitter.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
//import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "user_detail")
public class UserDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	private String name;
	private String email;
	private String password;
	private int status;
	@Column(name="ACCOUNT_EXPIRED")
	private boolean accountExpired;
	@Column(name="ACCOUNT_LOCKED")
	private boolean accountLocked;
	@Column(name="CREDENTIALS_EXPIRED")
	private boolean credentialExpired;
	@Column(name="ENABLED")
	private boolean enabled;
	private Date dob;
	@JoinTable(name = "friend_mapping", joinColumns = @JoinColumn(name ="user_id"),inverseJoinColumns = @JoinColumn(name ="friend_id"))
	@ManyToMany
	private List<UserDetail> friends;
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "role_user_mapping", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
//	private List<Role> roles;
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//	private List<Post> posts;
	

//public List<Post> getPosts() {
//		return posts;
//	}
//	public void setPosts(List<Post> posts) {
//		this.posts = posts;
//	}
	//	public UserDetail(String userName, long id, String token, List<GrantedAuthority> grantedAuthorities) {
//		this.name = userName;
//		this.id = Integer.parseInt(id);
////		this.token = token;
//		this.role = grantedAuthorities;
//	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
//	public List<Role> getRoles() {
//		return roles;
//	}
//	public void setRoles(List<Role> roles) {
//		this.roles = roles;
//	}
	public boolean isAccountExpired() {
		return accountExpired;
	}
	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}
	public boolean isAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
	public boolean isCredentialExpired() {
		return credentialExpired;
	}
	public void setCredentialExpired(boolean credentialExpired) {
		this.credentialExpired = credentialExpired;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
//	@Override
//	public String toString() {
//		return "UserDetail [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", status="
//				+ status + ", accountExpired=" + accountExpired + ", accountLocked=" + accountLocked
//				+ ", credentialExpired=" + credentialExpired + ", enabled=" + enabled + ", roles=" + roles + "]";
//	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
