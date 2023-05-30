package com.gamboSupermarket.application.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamboSupermarket.application.dto.OtpDetails;
import com.gamboSupermarket.application.model.ApiCallResponse;
import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.OtpRequest;
import com.gamboSupermarket.application.model.OtpResponse;
import com.gamboSupermarket.application.repository.OtpRepository;
import com.gamboSupermarket.application.utils.APICallUtility;
import com.gamboSupermarket.application.utils.Constant;
import com.gamboSupermarket.application.utils.ResponseUtils;

@Service
public class OtpService implements IOtpService {

	private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

	@Value("${2factor.url}")
	private String otpUrl;

	@Value("${2factor.otpTemplate}")
	private String otpTemplate;

	@Autowired
	private APICallUtility apiUtility;

	@Autowired
	private OtpRepository otpRepository;

	@Override
	public CustomResponse<Object> getOtp(String mobileNumber, HttpServletRequest request)
			throws URISyntaxException, IOException {
		StringBuilder urlBuilder = new StringBuilder(otpUrl);
		urlBuilder.append("SMS/");
		urlBuilder.append(mobileNumber);
		urlBuilder.append(Constant.OTPAUTOGEN);
		urlBuilder.append(otpTemplate);

		Map<String, String> headers = new HashMap<>();
		headers.put("content-type", "application/x-www-form-urlencoded");

		ApiCallResponse apiCallResponse = apiUtility.getRequest(urlBuilder.toString(), new HashMap<>(), headers, null,
				"getOtp");
		if (apiCallResponse.getStatusCode() != 200) {
			// throw some exception
		}
		ObjectMapper mapper = new ObjectMapper();
		OtpResponse otpResponse = mapper.readValue(apiCallResponse.getResponseBody(), OtpResponse.class);
		saveOtpDetails(mobileNumber, otpResponse);
		return ResponseUtils.getCustomResponse(200, otpResponse, "otp sent successfully", request);
	}

	private void saveOtpDetails(String mobileNumber, OtpResponse otpResponse) {

		OtpDetails otpDetailDto = otpRepository.findByMobileNumber(mobileNumber);
		if (otpDetailDto == null) {
			otpDetailDto = new OtpDetails();
			otpDetailDto.setMobileNumber(mobileNumber);
		}
		otpDetailDto.setTxnId(otpResponse.getDetails());
		otpDetailDto.setStatus("pending");

		otpRepository.save(otpDetailDto);

	}

	@Override
	public CustomResponse<Object> verifyOtp(OtpRequest otpRequest, HttpServletRequest request)
			throws URISyntaxException, IOException {

		OtpDetails otpDetailDto = otpRepository.findByMobileNumber(otpRequest.getMobileNumber());
		if (otpDetailDto == null) {
			// throw some exception
		}
		StringBuilder urlBuilder = new StringBuilder(otpUrl);
		urlBuilder.append(Constant.VERIFY);
		urlBuilder.append(otpRequest.getTxnId());
		urlBuilder.append("/");
		urlBuilder.append(otpRequest.getOtp());

		Map<String, String> headers = new HashMap<>();
		headers.put("content-type", "application/x-www-form-urlencoded");

		ApiCallResponse apiCallResponse = apiUtility.getRequest(urlBuilder.toString(), new HashMap<>(), headers, null,
				"getOtp");
		if (apiCallResponse.getStatusCode() != 200) {
			// throw some exception
		}
		ObjectMapper mapper = new ObjectMapper();
		OtpResponse otpResponse = mapper.readValue(apiCallResponse.getResponseBody(), OtpResponse.class);
		otpDetailDto.setStatus("verified");
		otpRepository.save(otpDetailDto);
		return ResponseUtils.getCustomResponse(200, otpResponse, "otp verified successfully", request);
	}

	@Override
	public void generateSMS(String mobileNumber, String customerName, String orderRefNumber) throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder(otpUrl);
		urlBuilder.append(Constant.ADDONSMS);
		String responseBody = null;
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			MultipartEntityBuilder builder = MultipartEntityBuilder.create()
					.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			Map<String, String> map =  new HashMap<>();
			map.put("From", "BIGORD");
			map.put("To", mobileNumber);
			map.put("TemplateName", "OrderDetail");
			map.put("VAR1", customerName);
			map.put("VAR2", orderRefNumber);
			map.put("VAR3", "placed successfully");
			System.out.println("output  ===>  " + map);
			map.forEach((key, value) -> {
				builder.addTextBody(key, value);
				System.out.println("key : " + key + " value : " + value);
			});

			HttpEntity requestdData = builder.build();
			HttpUriRequest request = RequestBuilder.post(urlBuilder.toString())
					.setEntity(requestdData).build();
			System.out.println("Executing request " + request.getRequestLine() + " " + request);
			// Create a custom response handler
			ResponseHandler<String> responseHandler = response -> {

				int status = response.getStatusLine().getStatusCode();
				System.out.println("status code http" + status + "resonse http" + response);
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					// System.out.println(EntityUtils.toString(entity));
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			responseBody = httpclient.execute(request, responseHandler);
			System.out.println(responseBody);

		}
		
	}

}
