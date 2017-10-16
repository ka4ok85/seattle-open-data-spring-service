package com.example.exceptions.dto;

public class AuthenticationLogin {

	private String login;
	private String role;

	public AuthenticationLogin(String login, String role) {
		super();
		this.login = login;
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AuthenticationLogin [login=" + login + ", role=" + role + "]";
	}

}
