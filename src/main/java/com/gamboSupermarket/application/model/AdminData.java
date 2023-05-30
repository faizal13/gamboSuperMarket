package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class AdminData {

	private long pendingOrders;
	private long cancelledOrders;
	private long processedOrder;
	private double totalIncome;

}
