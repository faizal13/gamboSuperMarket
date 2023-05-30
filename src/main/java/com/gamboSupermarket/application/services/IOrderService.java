package com.gamboSupermarket.application.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gamboSupermarket.application.model.AdminData;
import com.gamboSupermarket.application.model.BookedProductDetail;
import com.gamboSupermarket.application.model.CheckOutResponse;
import com.gamboSupermarket.application.model.CustomerAddress;
import com.gamboSupermarket.application.model.DeliveryDateRequest;
import com.gamboSupermarket.application.model.GetOrdersResponse;
import com.gamboSupermarket.application.model.OrderInfo;
import com.gamboSupermarket.application.model.SingleObjectRequest;
import com.gamboSupermarket.application.model.UpdateAddressRequest;
import com.gamboSupermarket.application.model.UpdateMobileNumberRequest;

public interface IOrderService {
	
	public CheckOutResponse checkoutOrder(HttpServletRequest request, String refNumber);
	
	public void updateMobileNumber(HttpServletRequest request, UpdateMobileNumberRequest updateMobileNumberRequest);
	
	public void updateAddress(HttpServletRequest request, UpdateAddressRequest updateAddressRequest);
	
	public CustomerAddress getAddresses();
	
	public List<BookedProductDetail> placeOrder(HttpServletRequest request, String refNumber) throws Exception;
	
	public GetOrdersResponse getAllOrders(Integer pageNo, Integer pageSize);
	
	public AdminData getData();
	
	public void updateStatus(SingleObjectRequest request);
	
	public void updateDeliveryTime(HttpServletRequest request, DeliveryDateRequest deliveryDateRequest);
	
	public OrderInfo getOrderSummary(HttpServletRequest request, String refNumber);
	
	public List<OrderInfo> getMyOrders();
	
}
