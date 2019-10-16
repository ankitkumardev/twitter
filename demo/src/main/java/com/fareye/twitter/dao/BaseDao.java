package com.fareye.twitter.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {
	@PersistenceContext
	protected EntityManager entityManager;

	public BaseDao() {
	}

	public Session currentSession() {
		return entityManager.unwrap(Session.class);
	}
}
