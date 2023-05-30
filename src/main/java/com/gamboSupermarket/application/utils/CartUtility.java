
package com.gamboSupermarket.application.utils;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.gamboSupermarket.application.dto.OrderDetail;
import com.gamboSupermarket.application.model.CartInfo;

public class CartUtility {

	public static CartInfo getCartInSession(HttpServletRequest request, String refNumber) {

		CartInfo cartInfo = null;
		if (refNumber != null) {
			cartInfo = (CartInfo) request.getSession().getAttribute(refNumber);
		}

		if (cartInfo == null) {
			cartInfo = new CartInfo();
			refNumber = UUID.randomUUID().toString();
			cartInfo.setOrderNum(refNumber);
			request.getSession().setAttribute(refNumber, cartInfo);
		}

		return cartInfo;
	}

	public static void removeCartInSession(HttpServletRequest request) {
		request.getSession().removeAttribute("myCart");
	}

	public static void storeLastOrderedCartInSession(HttpServletRequest request, CartInfo cartInfo) {
		request.getSession().setAttribute("lastOrderedCart", cartInfo);
	}

	public static CartInfo getLastOrderedCartInSession(HttpServletRequest request) {
		return (CartInfo) request.getSession().getAttribute("lastOrderedCart");
	}

	public static double getSavingAmount(List<OrderDetail> orderDetailList) {
		double total = getOriginalAmountTotal(orderDetailList) - getAmountTotal(orderDetailList);
		return total;
	}

	public static double getAmountTotal(List<OrderDetail> orderDetailList) {
		double total = 0;
		for (OrderDetail line : orderDetailList) {
			total += line.getAmount();
		}
		return total;
	}

	public static double getOriginalAmountTotal(List<OrderDetail> orderDetailList) {
		double total = 0;
		for (OrderDetail line : orderDetailList) {

			total += line.getProduct().getPrice() * line.getQuanity();
		}
		return total;
	}

	public static float getPercentDiscount(double markedPrice, double sellingPrice) {
		float discount = (float) (markedPrice - sellingPrice);

		float disPercent = (discount / (float) markedPrice) * 100;

		return disPercent;
	}

}
