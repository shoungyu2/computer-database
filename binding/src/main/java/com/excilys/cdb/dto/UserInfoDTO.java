package com.excilys.cdb.dto;

public class UserInfoDTO {

	private final String id;
	private final String username;
	private final String password;
	
	private UserInfoDTO(Builder b) {
		this.id=b.id;
		this.username=b.username;
		this.password=b.password;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public static class Builder{
		
		private final String id;
		private final String username;
		private final String password;
		
		public Builder(String id, String username, String password) {
			this.id=id;
			this.username=username;
			this.password=password;
		}
		
		public UserInfoDTO build() {
			return new UserInfoDTO(this);
		}
		
	}
	
}
