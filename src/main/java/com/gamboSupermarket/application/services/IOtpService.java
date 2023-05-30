package com.gamboSupermarket.application.services;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.OtpRequest;

public interface IOtpService {

	public CustomResponse<Object> getOtp(String mobileNumber, HttpServletRequest request)
			throws URISyntaxException, IOException;
	
	public CustomResponse<Object> verifyOtp(OtpRequest otpRequest, HttpServletRequest request)
			throws URISyntaxException, IOException;
	
	public void generateSMS(String mobileNumber, String customerName, String orderRefNumber) throws IOException;

}
