
package com.gamboSupermarket.application.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CartInfo {

	@Id
	private String orderNum;

	private Long userId;

	private List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

	public CartInfo() {

	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CartLineInfo> getCartLines() {
		return this.cartLines;
	}

	private CartLineInfo findLineByCode(long code) {
		for (CartLineInfo line : this.cartLines) {
			if (line.getProductInfo().getId().equals(code)) {
				return line;
			}
		}
		return null;
	}

	public void addProduct(ProductInfo productInfo, int quantity) {
		CartLineInfo line = this.findLineByCode(productInfo.getId());
		int newQuantity = 0;
		if (line == null) {
			line = new CartLineInfo();
			line.setQuantity(quantity);
			line.setProductInfo(productInfo);
			newQuantity = quantity;
			this.cartLines.add(line);
		}else {
			newQuantity = line.getQuantity() + quantity;
		}
		if (newQuantity <= 0) {
			this.cartLines.remove(line);
		} else {
			line.setQuantity(newQuantity);
		}
	}

	public void validate() {

	}

	public void updateProduct(long code, int quantity) {
		CartLineInfo line = this.findLineByCode(code);

		if (line != null) {
			if (quantity <= 0) {
				this.cartLines.remove(line);
			} else {
				line.setQuantity(quantity);
			}
		}
	}

	public void removeProduct(ProductInfo productInfo) {
		CartLineInfo line = this.findLineByCode(productInfo.getId());
		if (line != null) {
			this.cartLines.remove(line);
		}
	}

	public boolean isEmpty() {
		return this.cartLines.isEmpty();
	}

	/*
	 * public boolean isValidCustomer() { return this.customerInfo != null &&
	 * this.customerInfo.isValid(); }
	 */
	public int getQuantityTotal() {
		int quantity = 0;
		for (CartLineInfo line : this.cartLines) {
			quantity += line.getQuantity();
		}
		return quantity;
	}

	public double getAmountTotal() {
		double total = 0;
		for (CartLineInfo line : this.cartLines) {
			total += line.getAmount();
		}
		return total;
	}

	public double getOriginalAmountTotal() {
		double total = 0;
		for (CartLineInfo line : this.cartLines) {
			total += line.getOldAmount();
		}
		return total;
	}

	public double getSavingAmount() {
		double total = getOriginalAmountTotal() - getAmountTotal();
		return total;
	}

	public void updateQuantity(CartInfo cartForm) {
		if (cartForm != null) {
			List<CartLineInfo> lines = cartForm.getCartLines();
			for (CartLineInfo line : lines) {
				this.updateProduct(line.getProductInfo().getId(), line.getQuantity());
			}
		}

	}

}
