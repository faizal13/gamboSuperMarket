package com.gamboSupermarket.application.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gamboSupermarket.application.model.ApiCallResponse;
import com.gamboSupermarket.application.model.RequestConfiguration;

@Component
public class APICallUtility {

	private static final Logger logger = LoggerFactory.getLogger(APICallUtility.class);

	public ApiCallResponse postRequest(String url, String requestBodyString, Map<String, String> headers,
			RequestConfiguration reqConfiguration, String apiName, boolean loggable) throws IOException {

		if (loggable) {
			logger.info("Request String--->" + requestBodyString);
			logger.info("Request with url:" + url + "and apiName: " + apiName + " is being logged");
		}

		HttpClientBuilder builder = getHttpClientBuilder(reqConfiguration);

		HttpResponse response = null;
		try (CloseableHttpClient client = Objects.nonNull(builder) ? builder.build() : HttpClients.createDefault()) {
			HttpPost postRequest = new HttpPost(url);
			StringEntity entity = new StringEntity(requestBodyString);

			headers.forEach(postRequest::addHeader);

			postRequest.setEntity(entity);
			response = client.execute(postRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			String responseBody = EntityUtils.toString(response.getEntity());
			Header[] responseHeaders = response.getAllHeaders();

			return createApiCallResponse(statusCode, responseBody, responseHeaders);
		}
	}

	public ApiCallResponse getRequest(String url, Map<String, String> paramMap, Map<String, String> headers,
			RequestConfiguration reqConfiguration, String apiName)
			throws URISyntaxException, IOException {

			logger.info("Request with url:" + url + "and apiName: " + apiName + " is being logged");

		HttpClientBuilder builder = getHttpClientBuilder(reqConfiguration);

		HttpResponse response = null;
		try (CloseableHttpClient client = Objects.nonNull(builder) ? builder.build() : HttpClients.createDefault()) {

			URIBuilder uriBuilder = new URIBuilder(url);

			List<NameValuePair> paramList = paramMap.entrySet().stream()
					.map(e -> new BasicNameValuePair(e.getKey(), e.getValue())).collect(Collectors.toList());

			uriBuilder.setParameters(paramList);

			url = uriBuilder.build().toString();
			HttpGet getRequest = new HttpGet(url);

			headers.forEach(getRequest::addHeader);
			response = client.execute(getRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			String responseBody = EntityUtils.toString(response.getEntity());
			Header[] responseHeaders = response.getAllHeaders();

			return createApiCallResponse(statusCode, responseBody, responseHeaders);

		}

		// add catch block here and in post request
		// for caching socket-timeout and connect timeout
		// check this post:
		// https://brian.olore.net/wp/2009/08/apache-httpclient-timeout/
		// or add timeout handling at caller side
		// to throw custom exception and maybe catch it in controller advice
		// and send an email in case of criticality

	}

	private HttpClientBuilder getHttpClientBuilder(RequestConfiguration reqConfiguration) {
		HttpClientBuilder builder = null;
		if (Objects.nonNull(reqConfiguration)) {
			RequestConfig.Builder requestBuilder = RequestConfig.custom()
					// .setConnectionRequestTimeout(reqConfiguration.getConnectionRequestTimeout())
					.setConnectTimeout(reqConfiguration.getConnectTimeout())
					.setSocketTimeout(reqConfiguration.getSocketTimeout());
			builder = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build());
		}
		return builder;
	}

	private ApiCallResponse createApiCallResponse(int statusCode, String responseBody, Header[] headers) {
		ApiCallResponse.ApiCallResponseBuilder builder = ApiCallResponse.builder();
		builder.statusCode(statusCode).responseBody(responseBody).headers(getAllHeaders(headers));
		return builder.build();
	}

	private String getAllHeaders(Header[] headers) {
		if (Objects.nonNull(headers)) {
			Map<String, String> headerMap = new HashMap<>();
			for (Header header : headers) {
				headerMap.put(header.getName(), header.getValue());
			}
			return headerMap.toString();
		}
		return null;
	}
}
