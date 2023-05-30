package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class UpdateAddressRequest {
	
	private String refNumber;

	private String customerName;
	
	private String customerEmail;
	
	private AddressInfo addressInfo;
	
	
}
