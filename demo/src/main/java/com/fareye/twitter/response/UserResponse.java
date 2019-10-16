package com.fareye.twitter.response;

import java.util.Date;

import com.fareye.twitter.myenum.FriendType;

public class UserResponse {
	private int id;
	private String firstName;
	private String lastName;
	private String name;
	private String email;
	private Date dob;
	private FriendType isFriend;
	
	public UserResponse(int id, String firstName, String lastName, String name, String email, Date dob) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = name;
		this.email = email;
		this.dob = dob;
//		this.isFriend = isFriend;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public FriendType getIsFriend() {
		return isFriend;
	}
	public void setIsFriend(FriendType isFriend) {
		this.isFriend = isFriend;
	}
}
