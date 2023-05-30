package com.gamboSupermarket.application.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class PlaceOrderRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String refNumber;
}
