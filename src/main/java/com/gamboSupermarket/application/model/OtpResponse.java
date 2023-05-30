package com.gamboSupermarket.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OtpResponse {

	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Details")
	private String details;
}
