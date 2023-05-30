package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class DeliveryDateRequest {


	private String refNumber;

	private String deliveryDate;
	
	private boolean expressDelivery;

}
