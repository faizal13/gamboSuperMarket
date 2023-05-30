package com.gamboSupermarket.application.model;

import java.util.List;

import lombok.Data;

@Data
public class UpdateCartInfoRequest {

	private List<AddToCartRequest> cartList;
}
