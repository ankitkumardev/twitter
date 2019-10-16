package com.fareye.twitter.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import com.fareye.twitter.entity.Post;
//import com.fareye.twitter.entity.Role;
import com.fareye.twitter.entity.UserDetail;
import com.fareye.twitter.jwt.JwtRequest;
import com.fareye.twitter.myenum.FriendType;
import com.fareye.twitter.response.HomeResponse;

@Repository
@Transactional
public class MainDao extends BaseDao {
	
	public Optional<UserDetail> getUserByName(String userName) {
		CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();	
		CriteriaQuery<UserDetail> criteria = criteriaBuilder.createQuery(UserDetail.class);
		Root<UserDetail> userRoot = criteria.from(UserDetail.class);
		Predicate restriction = criteriaBuilder.equal(userRoot.get("name"), userName);
		criteria.select(userRoot).where(restriction);
		return Optional.ofNullable(currentSession().createQuery(criteria).getSingleResult());
	}	

	public boolean register(UserDetail user) {
		user.setAccountExpired(false);
		user.setAccountLocked(false);
		user.setCredentialExpired(false);
		user.setEnabled(true);
		currentSession().save(user);
		return true;
	}

	public HomeResponse getHomeResonse(JwtRequest req) {
		HomeResponse hr = new HomeResponse();
		UserDetail user = getUserDetail(req);
		hr.setUser(user);
		hr.setPostList(getPostFromFriends(user.getId()));
		String sql = "SELECT count(*) FROM friend_mapping where user_id = :user_id";
		NativeQuery<BigInteger> query = currentSession().createSQLQuery(sql);
		query.setParameter("user_id", user.getId());
		BigInteger following  = query.getSingleResult();
		sql = "SELECT count(*) FROM friend_mapping where friend_id = :user_id";
		query = currentSession().createSQLQuery(sql);
		query.setParameter("user_id", user.getId());
		BigInteger followers = query.getSingleResult();
		hr.setFollowers(followers);
		hr.setFollowing(following);
		return hr;
	}
	public List<Post> getPostFromFriends(int userId) {
		String sql = "SELECT friend_mapping.friend_id FROM friend_mapping where user_id = :user_id";
		NativeQuery<Integer> query = currentSession().createSQLQuery(sql);
//		query.addEntity(Employee.class);
		query.setParameter("user_id", userId);
		List<Integer> friendId = query.list();
		friendId.add(userId);
		List<Post> posts = currentSession().createCriteria(Post.class).createAlias("user", "user").add(Restrictions.in("user.id", friendId)).addOrder(Order.desc("date")).list();
		return posts;
		
	}
	public UserDetail getUserDetail(JwtRequest req) {
		CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();	
		CriteriaQuery<UserDetail> criteria = criteriaBuilder.createQuery(UserDetail.class);
		Root<UserDetail> userRoot = criteria.from(UserDetail.class);
		Predicate restriction = criteriaBuilder.equal(userRoot.get("name"), req.getUsername());
		criteria.select(userRoot).where(restriction);
		UserDetail user = currentSession().createQuery(criteria).getSingleResult();
		return user;
	}
	public boolean makeFriend(int userId,int frienId) {
		
		entityManager.createNativeQuery(
			    "INSERT INTO friend_mapping VALUES (?, ?)")
			    .setParameter(1, userId)
			    .setParameter(2, frienId)
			    .executeUpdate();
		return true;
	}

	public Post insertPost(JwtRequest req, Post post) {
		UserDetail user = getUserDetail(req);
//		user.setPosts(null);
		post.setUser(user);
		currentSession().save(post);
		return post;
	}

	public boolean removeFriend(int userId, int frienId) {
		entityManager.createNativeQuery(
			    "delete from friend_mapping where user_id =? and friend_id = ?")
			    .setParameter(1, userId)
			    .setParameter(2, frienId)
			    .executeUpdate();
		return true;
		
	}

	public List<UserDetail> getFriends(JwtRequest req) {
		UserDetail user = getUserDetail(req);
		String sql = "SELECT friend_mapping.friend_id FROM friend_mapping where user_id = :user_id";
		NativeQuery<Integer> query = currentSession().createSQLQuery(sql);
//		query.addEntity(Employee.class);
		query.setParameter("user_id", user.getId());
		List<Integer> friendId = query.list();
		List<UserDetail> users = currentSession().createCriteria(UserDetail.class).add(Restrictions.in("id", friendId)).list();
		return users;
	}

	public List<UserDetail> getNonFriends(JwtRequest req) {
		UserDetail user = getUserDetail(req);
		String sql = "SELECT friend_mapping.friend_id FROM friend_mapping where user_id = :user_id";
		NativeQuery<Integer> query = currentSession().createSQLQuery(sql);
		query.setParameter("user_id", user.getId());
		List<Integer> friendId = query.list();
		List<UserDetail> users = currentSession().createCriteria(UserDetail.class).add(Restrictions.not(Restrictions.in("id", friendId))).list();
		return users;
	}

	public boolean checkUserName(String user) {
		List<UserDetail> users = currentSession().createCriteria(UserDetail.class).add(Restrictions.eqOrIsNull("name", user)).list();
		if(users.isEmpty()) 
			return true;
		return false;
	}

	public List<UserDetail> searchPeople(String searchText) {
		CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();	
		CriteriaQuery<UserDetail> criteria = criteriaBuilder.createQuery(UserDetail.class);
		Root<UserDetail> userRoot = criteria.from(UserDetail.class);
		Predicate restriction = criteriaBuilder.or( criteriaBuilder.like(userRoot.get("name"), searchText), 
													criteriaBuilder.like(userRoot.get("lastName"), searchText), 
													criteriaBuilder.like(userRoot.get("firstName"), searchText));
		criteria.select(userRoot).where(restriction);
		return currentSession().createQuery(criteria).list();
	}

	public FriendType checkFriendShip(int friendId, UserDetail selfUser) {
		if(friendId == selfUser.getId()) return FriendType.SELF;
		String sql = "SELECT count(*) FROM friend_mapping where user_id = :user_id and friend_id= :friend_id";
		NativeQuery<BigInteger> query = currentSession().createSQLQuery(sql);
		query.setParameter("user_id", selfUser.getId());
		query.setParameter("friend_id", friendId);
		BigInteger  count = query.getSingleResult();
		if(count.equals(BigInteger.ZERO)) return FriendType.NOTFRIEND;
		else return FriendType.FRIEND;
	}
}
