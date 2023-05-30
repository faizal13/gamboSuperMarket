package com.gamboSupermarket.application.model;

import java.util.Set;

import lombok.Data;

@Data
public class SignUpRequest {

	private String name;

	private String emailId;

	private String mobileNumber;

	private String password;

	private Set<String> role;

}
