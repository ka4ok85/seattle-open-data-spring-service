package com.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = -1780004587184647821L;
	private final String username;
	private final String email;
	private final String role;
	private final String status;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;

	public JwtUser(String username, String email, String password, String role, String status,
			Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.email = email;
		this.role = role;
		this.status = status;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "JwtUser [username=" + username + ", password=[password]" + ", authorities=" + authorities + "]";
	}

}