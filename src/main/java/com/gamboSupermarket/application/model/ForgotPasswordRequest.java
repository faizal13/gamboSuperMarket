package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

	private String mobileNumber;
	private String password;
	private String confirmPassword;

}
