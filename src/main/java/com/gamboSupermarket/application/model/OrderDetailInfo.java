package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class OrderDetailInfo {

	private SingleObjectRequest productInfo;
	private int quanity;
	private double price;
	private double amount;

}
