
package com.gamboSupermarket.application.model;

import com.gamboSupermarket.application.dto.Product;
import com.gamboSupermarket.application.utils.CartUtility;

import lombok.Data;

@Data
public class ProductInfo {

	private Long id;
	private String name;
	private double price;
	private double oldPrice;
	private byte[] productImageByte;
	private float percentDiscount;

	public ProductInfo() {
	}

	public ProductInfo(Product product) {
		this.id = product.getId();
		this.name = product.getProductName();
		this.price = product.getDiscountPrice();
		this.oldPrice = product.getPrice();
		this.productImageByte = product.getProductImageByte();
		this.percentDiscount = product.getPercentDiscount();
		// Utility.decompressBytes(product.getProductImageByte());
	}

}
