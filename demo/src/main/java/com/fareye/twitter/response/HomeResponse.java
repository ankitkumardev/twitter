package com.fareye.twitter.response;

import java.math.BigInteger;
import java.util.List;

import com.fareye.twitter.entity.Post;
import com.fareye.twitter.entity.UserDetail;

public class HomeResponse {
	private UserDetail user;
	private List<Post> postList;
	private BigInteger followers;
	private BigInteger following;
	public BigInteger getFollowers() {
		return followers;
	}
	public void setFollowers(BigInteger followers) {
		this.followers = followers;
	}
	public BigInteger getFollowing() {
		return following;
	}
	public void setFollowing(BigInteger following) {
		this.following = following;
	}
	public UserDetail getUser() {
		return user;
	}
	public void setUser(UserDetail user) {
		this.user = user;
	}
	public List<Post> getPostList() {
		return postList;
	}
	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}
	

}
