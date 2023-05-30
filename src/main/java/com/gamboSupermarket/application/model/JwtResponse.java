package com.gamboSupermarket.application.model;

import java.util.List;

import com.gamboSupermarket.application.dto.User;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private List<String> roles;
	private String name;
	private String role;
	private String refNumber;

	public JwtResponse() {
	}

	public JwtResponse(String accessToken, Long id, String username, List<String> roles, String name,
			String refNumber) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.name = name;
		this.refNumber = refNumber;
		this.role = roles.get(0);
	}

	public JwtResponse(String accessToken, User user) {
		user.setPassword(null);
		this.token = accessToken;
	}
}
