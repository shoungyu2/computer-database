package com.excilys.cdb.persistence;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.cdb.model.QUserInfo;
import com.excilys.cdb.model.UserInfo;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
@EnableTransactionManagement
public class UserInfoDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Optional<UserInfo> getUserbyId(int id){
		
		JPAQuery<UserInfo> query = new JPAQuery<UserInfo>(entityManager);
		QUserInfo userInfo = QUserInfo.userInfo;
		UserInfo ui = query.from(userInfo)
				.where(userInfo.id.eq(id))
				.fetchOne();
		return Optional.ofNullable(ui);
		
	}
	
	public boolean createUser(UserInfo userInfo) {
		
		entityManager.persist(userInfo);
		return true;
		
	}
	
}
