package com.gamboSupermarket.application.utils;

public final class Constant {

	private Constant() {

	}

	// API Exception
	public static final String STATUS_CODE_501_DESC = "Internal Server Error! Please try again.";
	public static final String STATUS_CODE_400_DESC = "Bad Request. Please check the request and try again!";
	public static final String STATUS_CODE_502_DESC = "Failed to process details!";
	public static final String STATUS_CODE_503_DESC = "Failed to process details!";
	public static final String STATUS_CODE_500_DESC = "Internal Server error. Contact support!";
	public static final String FIELD_VALIDATION_ERROR = "Field Validation Failed";

	// Query Related
	public static final String PREFIX = "insert into ";
	public static final String OPENBRACKET = "(";
	public static final String CLOSEBRACKET = ")";
	public static final String VALUES = " values ";
	public static final String UPDATE = " update ";
	public static final String SET = " set ";
	public static final String WHERE = " where ";

	public static final String AUDIT_TRAIL_REQUEST = "REST-WS-REQUEST";
	public static final String AUDIT_TRAIL_RESPONSE = "REST-WS-RESPONSE";

	public static final String CREATE_REQUEST = "CREATE";
	public static final String UPDATE_REQUEST = "UPDATE";

	public static final String REQUEST_SUCCESS = "SUCCESS";
	public static final String REQUEST_FAIL = "FAIL";

	public static final String REQUEST_TOKEN = "requestToken";
	public static final String LEAD_ID = "leadId";
	public static final String MESSAGE = "message";
	public static final String LEAD_MESSAGE_IN_PROGRESS = "LeadId Create request in Process.";
	public static final String BRE_1_MESSAGE_IN_PROGRESS = "LeadId BRE_1 request in Process.";
	public static final String BRE_2_MESSAGE_IN_PROGRESS = "LeadId BRE_2 request in Process.";
	public static final String CALL_BACK_RESPONSE_STATUS = "callBackResponseStatus";

	public static final String PERSONAL_INFO = "personalInfo";
	public static final String EMPLOYMENT_INFO = "employmentInfo";
	public static final String ADDITIONAL_INFO = "additionalInfo";
	public static final String BASIC_INFO = "basicInfo";
	public static final String BRE_CALL = "breCall";
	public static final String USER_NAME = "userName";
	public static final String PASS_WORD = "password";
	public static final String ACTION_NAME_TAG = "actionName";
	public static final String APPLICATION_ID_TAG = "applicationId";
	public static final String USER_TYPE_TAG = "userType";

	public static final String REQUEST_TYPE = "requestType";

	// SSE Request types
	public static final String SSE_REQUEST_LEAD_CREATE = "CREATE";
	public static final String SSE_REQUEST_LEAD_UPDATE = "UPDATE";
	public static final String SSE_REQUEST_LEAD = "LEAD";
	public static final String SSE_REQUEST_BRE_1 = "BRE_1";
	public static final String SSE_REQUEST_BRE_2 = "BRE_2";
	public static final String DOC_UPLOAD_REQUEST = "DOC_UPLOAD";

	public interface CallBackResponseStatus {
		String SUCCESS = "success";
		String IN_PROGRESS = "in_progress";
		String FAILURE = "failure";
		String ERROR = "error";
	}

	public static final String CONTENT_TYPE_JSON_UTF_8 = "application/json;charset=UTF-8";
	public static final String DECRYPT = "decrypt";
	public static final String ENCRYPT = "encrypt";
	public static final String ENCRYPT_DECRYPT_KEY_FACTOR_AES = "AES";

	public static final String SSE_CONSTANT_KEY = "OlXoRFMQuJ449dTgPzoLjeJhixBZOL4JxUuHu9jWOYQ="; // "UxV9BnDFCeTZZv6Hf4WkxHBJvs9PvojLyHDeY7Eny48=";
	public static final String SSE_CONSTANT_KEY_REQUEST_TYPE = "sseConstantKeyRequestType";

	public static final String REQUEST_MAP = "requestMap";
	public static final String METADATA_MAP = "metadataMap";
	
	
	public static final String DATA = "data";
	public static final String OPERATION = "operation";
	
	public static final String OTPAUTOGEN = "/AUTOGEN/";
	public static final String VERIFY = "VERIFY/";
	public static final String ADDONSMS = "ADDON_SERVICES/SEND/TSMS";
	
}
