package com.gamboSupermarket.application.model;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ApiCallResponse {

	private int statusCode;

	private String responseBody;

	private String headers;

	ApiCallResponse(int statusCode, String responseBody, String headers) {
		this.statusCode = statusCode;
		this.responseBody = responseBody;
		this.headers = headers;
	}

}
