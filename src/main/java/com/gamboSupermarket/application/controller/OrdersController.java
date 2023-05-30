package com.gamboSupermarket.application.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamboSupermarket.application.model.AddressInfo;
import com.gamboSupermarket.application.model.BookedProductDetail;
import com.gamboSupermarket.application.model.CheckOutResponse;
import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.CustomerAddress;
import com.gamboSupermarket.application.model.DeliveryDateRequest;
import com.gamboSupermarket.application.model.OrderInfo;
import com.gamboSupermarket.application.model.PlaceOrderRequest;
import com.gamboSupermarket.application.model.UpdateAddressRequest;
import com.gamboSupermarket.application.model.UpdateMobileNumberRequest;
import com.gamboSupermarket.application.services.IOrderService;
import com.gamboSupermarket.application.utils.Utility;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(path = "/api/order")
public class OrdersController {

	@Autowired
	private IOrderService orderService;

	@PostMapping(value = "/checkout")
	public ResponseEntity<CustomResponse<CheckOutResponse>> checkout(@RequestBody PlaceOrderRequest placeOrderRequest,
			HttpServletRequest request) throws IOException {
		CheckOutResponse checkout = orderService.checkoutOrder(request, placeOrderRequest.getRefNumber());
		CustomResponse<CheckOutResponse> response = new CustomResponse<>(200, "Success", checkout);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/addOrUpdateMobile")
	public ResponseEntity<CustomResponse<Object>> updateNumber(
			@RequestBody UpdateMobileNumberRequest updateMobileNumberRequest, HttpServletRequest request)
			throws IOException {

		orderService.updateMobileNumber(request, updateMobileNumberRequest);
		CustomResponse<Object> response = new CustomResponse<>(200, "Success",
				updateMobileNumberRequest.getRefNumber());
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(path = { "/getAllAddress" })
	public ResponseEntity<CustomResponse<CustomerAddress>> getAllAddress() throws IOException {

		CustomerAddress addresses = orderService.getAddresses();
		CustomResponse<CustomerAddress> response = new CustomResponse<>(200, "Success", addresses);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/addOrUpdateAddress")
	public ResponseEntity<CustomResponse<Object>> updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest,
			HttpServletRequest request) throws IOException {

		orderService.updateAddress(request, updateAddressRequest);
		CustomResponse<Object> response = new CustomResponse<>(200, "Success", updateAddressRequest.getRefNumber());
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/saveDeliveryDate")
	public ResponseEntity<CustomResponse<Object>> saveDeliveryDate(@RequestBody DeliveryDateRequest deliveryDateRequest,
			HttpServletRequest request) throws IOException {
		orderService.updateDeliveryTime(request, deliveryDateRequest);
		CustomResponse<Object> response = new CustomResponse<>(200, "Success", deliveryDateRequest.getRefNumber());
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/placeorder")
	public ResponseEntity<CustomResponse<List<BookedProductDetail>>> placeOrder(
			@RequestBody PlaceOrderRequest placeOrderRequest, HttpServletRequest request) throws Exception {
		CustomResponse<List<BookedProductDetail>> response = null;
		List<BookedProductDetail> bookedProductStatusList = orderService.placeOrder(request,
				placeOrderRequest.getRefNumber());
		if (Utility.isEmpty(bookedProductStatusList)) {
			response = new CustomResponse<>(200, "Success", null);
		} else {
			response = new CustomResponse<>(501, "Stock unavailable", bookedProductStatusList);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/orderSummary")
	public ResponseEntity<CustomResponse<OrderInfo>> orderSummary(@RequestBody PlaceOrderRequest placeOrderRequest,
			HttpServletRequest request) throws IOException {
		OrderInfo order = orderService.getOrderSummary(request, placeOrderRequest.getRefNumber());
		CustomResponse<OrderInfo> response = new CustomResponse<>(200, "Success", order);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = { "/myOrders" })
	public ResponseEntity<CustomResponse<List<OrderInfo>>> myOrders() throws IOException {

		List<OrderInfo> myOrders = orderService.getMyOrders();
		CustomResponse<List<OrderInfo>> response = new CustomResponse<>(200, "Success", myOrders);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
