
package com.gamboSupermarket.application.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gamboSupermarket.application.dto.StatusMaster;

import lombok.Data;

@Data
public class OrderInfo {

	private String OrderId;
	private String orderDate;
	private double totalAmount;
	private String customerName;
	private String customerAddress;
	private String customerPhone;
	private StatusMaster status;
	private double deliveryCharge = 10.00;
	private double amount;

	List<OrderDetailInfo> OrderDetails = new ArrayList<>();
}
