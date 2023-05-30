package com.gamboSupermarket.application.model;

import java.util.List;

import lombok.Data;

@Data
public class GetOrdersResponse {

	private List<OrderInfo> orders;
	private int totalPages;

}
