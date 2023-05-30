package com.gamboSupermarket.application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CartInfoResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String refNumber;

	private CustomerInfo customerInfo;

	private double totalAmount;

	private double totalSaving;

	private double amount;

	private double deliveryCharge = 10.00;

	private List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

}
