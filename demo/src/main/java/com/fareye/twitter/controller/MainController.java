package com.fareye.twitter.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fareye.twitter.dao.MainDao;
import com.fareye.twitter.entity.Post;
import com.fareye.twitter.entity.UserDetail;
import com.fareye.twitter.jwt.JwtRequest;
import com.fareye.twitter.jwt.JwtValidator;
import com.fareye.twitter.response.HomeResponse;
import com.fareye.twitter.response.UserResponse;

@RestController
@RequestMapping("/rest")
@CrossOrigin
public class MainController {
	
	@Autowired
	MainDao mainDao;
	
	@Autowired
	JwtValidator validator;
	
	@GetMapping("/demo")
	public String demo() {	
		return "Hii";
		
	}
	@GetMapping("/home")
	public HomeResponse getHome(HttpServletRequest request) {
		Optional<String> optional = Optional.ofNullable(request.getHeader("Authentication"));
		optional
			.orElseThrow(()-> new RuntimeException("No Jwt token is present in headers"));
		String authentication = optional.get();
		JwtRequest req = validator.validate(authentication);
		return mainDao.getHomeResonse(req);
		
	}
	@GetMapping("/follow/{user}")
	public boolean followSomeone(@PathVariable("user") int friendId,HttpServletRequest request) {
		Optional<String> optional = Optional.ofNullable(request.getHeader("Authentication"));
		optional
			.orElseThrow(()-> new RuntimeException("No Jwt token is present in headers"));
		String authentication = optional.get();
		JwtRequest req = validator.validate(authentication);
		UserDetail user = mainDao.getUserDetail(req);
		mainDao.makeFriend(user.getId(), friendId);
		return true;
	}
	@GetMapping("/unfollow/{user}")
	public boolean unfollowSomeone(@PathVariable("user") int friendId,HttpServletRequest request) {
		Optional<String> optional = Optional.ofNullable(request.getHeader("Authentication"));
		optional
			.orElseThrow(()-> new RuntimeException("No Jwt token is present in headers"));
		String authentication = optional.get();
		JwtRequest req = validator.validate(authentication);
		UserDetail user = mainDao.getUserDetail(req);
		mainDao.removeFriend(user.getId(), friendId);
		return true;
	}
	
	@PostMapping("/post")
	public Post insertPost(@RequestBody Post post,HttpServletRequest request) {
		Optional<String> optional = Optional.ofNullable(request.getHeader("Authentication"));
		optional
			.orElseThrow(()-> new RuntimeException("No Jwt token is present in headers"));
		String authentication = optional.get();
		JwtRequest req = validator.validate(authentication);
		post.setDate(new Date());
		return mainDao.insertPost(req,post);
		
	}
	@GetMapping("/friends")
	public List<UserDetail> getFriends(HttpServletRequest request) {
		Optional<String> optional = Optional.ofNullable(request.getHeader("Authentication"));
		optional
			.orElseThrow(()-> new RuntimeException("No Jwt token is present in headers"));
		String authentication = optional.get();
		JwtRequest req = validator.validate(authentication);
		return mainDao.getFriends(req);
		
	}
	@GetMapping("/nonfriends")
	public List<UserDetail> getNonFriends(HttpServletRequest request) {
		Optional<String> optional = Optional.ofNullable(request.getHeader("Authentication"));
		optional
			.orElseThrow(()-> new RuntimeException("No Jwt token is present in headers"));
		String authentication = optional.get();
		JwtRequest req = validator.validate(authentication);
		return mainDao.getNonFriends(req);
		
	}
	@GetMapping("/search/{searchText}")
	public List<UserResponse> searchPeople(@PathVariable("searchText") String searchText,HttpServletRequest request) {
		searchText = "%"+searchText+"%";
		List<UserDetail> user =  mainDao.searchPeople(searchText);
		List<UserResponse> response = user.stream()
		.map(u-> new UserResponse(u.getId(),u.getFirstName(),u.getLastName(),u.getName(),u.getEmail(),u.getDob()))
		.collect(Collectors.toList());	
		Optional<String> optional = Optional.ofNullable(request.getHeader("Authentication"));
		optional
			.orElseThrow(()-> new RuntimeException("No Jwt token is present in headers"));
		String authentication = optional.get();
		JwtRequest req = validator.validate(authentication);
		UserDetail selfUser = mainDao.getUserDetail(req);
		response.stream().forEach(u-> u.setIsFriend(mainDao.checkFriendShip(u.getId(),selfUser)));
		return response;		
	}

}
