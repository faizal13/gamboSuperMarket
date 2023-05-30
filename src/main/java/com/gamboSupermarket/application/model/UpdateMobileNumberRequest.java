package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class UpdateMobileNumberRequest {
 
	private String refNumber;
	
	private String mobileNumber;
	
	private boolean mobileNumberVerified;
}
