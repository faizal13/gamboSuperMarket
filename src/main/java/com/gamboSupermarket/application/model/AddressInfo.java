package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class AddressInfo {

	private Long id;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String pincode;

	private boolean homeAddress;

	private boolean officeAddress;
}
