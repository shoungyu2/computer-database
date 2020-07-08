package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="UserInfo")
@Table(name="userinfo")
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final int id;
	
	@Column(name="username")
	private final String username;
	
	@Column(name="password")
	private final String password;
	
	private UserInfo(Builder b) {
		this.id=b.id;
		this.username=b.username;
		this.password=b.password;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public static class Builder{
		
		private final int id;
		private final String username;
		private final String password;
		
		public Builder(int id, String username, String password) {
			this.id=id;
			this.username=username;
			this.password=password;
		}
		
		public UserInfo build() {
			return new UserInfo(this);
		}
		
	}
	
}
