package com.gamboSupermarket.application.utils;

import javax.servlet.http.HttpServletRequest;

import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.EmptyJsonResponse;


public class ResponseUtils {

	/*
	 * public static CustomResponse<Object> getTimeOutResponse() { return new
	 * CustomResponse<>(300,
	 * "The Request has timed out at the Server. Please try again after some time",
	 * new EmptyJsonResponse(), System.currentTimeMillis(), "",""); }
	 */

	public static CustomResponse<Object> getCustomResponse(int statusCode, Object data, String description,
			HttpServletRequest request) {
		CustomResponse<Object> customResponse = new CustomResponse<Object>();
		customResponse.setData(data);
		customResponse.setDescription(description);
		customResponse.setStatus(statusCode);
		customResponse.setRequestPath(null == request.getServletPath() ? "" : request.getServletPath());
		return customResponse;
	}

	private ResponseUtils() {
	}

}
