package com.gamboSupermarket.application.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.OtpRequest;
import com.gamboSupermarket.application.services.IOtpService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(path = "/api/auth/otp")
public class OtpController {

	private static final Logger logger = LoggerFactory.getLogger(OtpController.class);

	@Autowired
	IOtpService otpService;

	@GetMapping(value = "/getOtp")
	public ResponseEntity<CustomResponse<Object>> getOtp(@RequestParam String mobileNumber, HttpServletRequest request)
			throws IOException, URISyntaxException {
		logger.debug("request" + mobileNumber);
		CustomResponse<Object> response = otpService.getOtp(mobileNumber, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/verifyOtp")
	public ResponseEntity<CustomResponse<Object>> verifyOtp(@RequestBody OtpRequest otpRequest,
			HttpServletRequest request) throws IOException, URISyntaxException {
		CustomResponse<Object> response = otpService.verifyOtp(otpRequest, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
