package com.gamboSupermarket.application.exceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.EmptyJsonResponse;
import com.gamboSupermarket.application.model.errormodels.ApiErrorsView;
import com.gamboSupermarket.application.model.errormodels.ApiFieldError;
import com.gamboSupermarket.application.model.errormodels.ApiGlobalError;
import com.gamboSupermarket.application.model.errormodels.FieldErrorResponse;
import com.gamboSupermarket.application.utils.Constant;
import com.gamboSupermarket.application.utils.ResponseUtils;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	protected Logger adviceLogger = LogManager.getLogger(this.getClass());

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

		BindingResult bindingResult = ex.getBindingResult();

		// add null check
		List<ApiFieldError> apiFieldErrors = bindingResult.getFieldErrors().stream()
				.map(fieldError -> new ApiFieldError(fieldError.getField(), fieldError.getCode(),
						fieldError.getRejectedValue()))
				.collect(Collectors.toList());

		List<ApiGlobalError> apiGlobalErrors = bindingResult.getGlobalErrors().stream()
				.map(globalError -> new ApiGlobalError(globalError.getCode())).collect(Collectors.toList());

		ApiErrorsView apiErrorsView = new ApiErrorsView(apiFieldErrors, apiGlobalErrors);
		FieldErrorResponse errorResponse = new FieldErrorResponse(apiErrorsView);

		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();

		CustomResponse<Object> response = ResponseUtils.getCustomResponse(30, errorResponse,
				Constant.FIELD_VALIDATION_ERROR, request);

		adviceLogger.error("For request path:" + request.getServletPath());
		adviceLogger.error("Field errors are: " + apiErrorsView);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(value = { NonTransientDataAccessException.class, RecoverableDataAccessException.class,
			ScriptException.class, TransientDataAccessException.class })
	protected ResponseEntity<CustomResponse<Object>> handleCrudRepositoryExceptions(DataAccessException ex,
			WebRequest webRequest) {
		adviceLogger.error("Database error occurred!", ex);
		int statusCode = 501;
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		CustomResponse<Object> customResponse = ResponseUtils.getCustomResponse(statusCode, new EmptyJsonResponse(),
				Constant.STATUS_CODE_501_DESC, request);
		return new ResponseEntity<>(customResponse, HttpStatus.OK);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

		adviceLogger.error("Bad Request recieved!", ex);
		int statusCode = 400;
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		CustomResponse<Object> customResponse = ResponseUtils.getCustomResponse(statusCode, new EmptyJsonResponse(),
				Constant.STATUS_CODE_400_DESC, request);
		return new ResponseEntity<>(customResponse, HttpStatus.OK);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<CustomResponse<Object>> handleException(Throwable ex, WebRequest webRequest) {
		adviceLogger.error("{}", ex);
		int statusCode = 500;
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		CustomResponse<Object> customResponse = ResponseUtils.getCustomResponse(statusCode, new EmptyJsonResponse(),
				Constant.STATUS_CODE_500_DESC, request);
		return new ResponseEntity<>(customResponse, HttpStatus.OK);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<CustomResponse<Object>> handleMaxSizeException(MaxUploadSizeExceededException ex,
			WebRequest webRequest) {
		adviceLogger.error("{}", ex);
		int statusCode = 500;
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		CustomResponse<Object> customResponse = ResponseUtils.getCustomResponse(statusCode, new EmptyJsonResponse(),
				"File too large!", request);
		return new ResponseEntity<>(customResponse, HttpStatus.EXPECTATION_FAILED);
	}

}
