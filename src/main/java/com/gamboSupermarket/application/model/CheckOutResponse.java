package com.gamboSupermarket.application.model;

import java.util.List;

import lombok.Data;

@Data
public class CheckOutResponse {

	private String mobilenumber;
	private CustomerAddress addresses;

}
