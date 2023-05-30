package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class OtpRequest {

	private String mobileNumber;

	private String txnId;

	private String otp;
}
