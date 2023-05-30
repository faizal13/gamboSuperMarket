package com.gamboSupermarket.application.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gamboSupermarket.application.dto.Address;
import com.gamboSupermarket.application.dto.Order;
import com.gamboSupermarket.application.dto.OrderDetail;
import com.gamboSupermarket.application.dto.Product;
import com.gamboSupermarket.application.dto.User;
import com.gamboSupermarket.application.model.AddressInfo;
import com.gamboSupermarket.application.model.AdminData;
import com.gamboSupermarket.application.model.BookedProductDetail;
import com.gamboSupermarket.application.model.CartInfo;
import com.gamboSupermarket.application.model.CartLineInfo;
import com.gamboSupermarket.application.model.CheckOutResponse;
import com.gamboSupermarket.application.model.CustomerAddress;
import com.gamboSupermarket.application.model.DeliveryDateRequest;
import com.gamboSupermarket.application.model.GetOrdersResponse;
import com.gamboSupermarket.application.model.OrderDetailInfo;
import com.gamboSupermarket.application.model.OrderInfo;
import com.gamboSupermarket.application.model.SingleObjectRequest;
import com.gamboSupermarket.application.model.UpdateAddressRequest;
import com.gamboSupermarket.application.model.UpdateMobileNumberRequest;
import com.gamboSupermarket.application.repository.AddressRepository;
import com.gamboSupermarket.application.repository.OrderRepository;
import com.gamboSupermarket.application.repository.ProductRepository;
import com.gamboSupermarket.application.repository.StatusRepository;
import com.gamboSupermarket.application.repository.UserRepository;
import com.gamboSupermarket.application.utils.Utility;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IProductService productService;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private IOtpService otpService;

	@Override
	public CheckOutResponse checkoutOrder(HttpServletRequest request, String refNumber) {

		// Order order = orderRepository.findByReferenceId(refNumber);
		/*
		 * CartInfo cartInfo = cartRepository.findById(refNumber).get(); if (cartInfo ==
		 * null) { // throw cartNotFoundException }
		 */
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userDetail = userRepository.findByUserNameOrMobileNumber(userDetails.getUsername());
		// User userDetail = userRepository.findById(userDetails.getUsername()).get();

		Order orderDto = orderRepository.findByReferenceId(refNumber);
		if (orderDto == null) {

			// throw some cartNotFound exception
			/*
			 * orderDto = new Order();
			 * 
			 * orderDto.setUserDetail(userDetail); orderDto.setReferenceId(refNumber);
			 */
		}
		// orderDto.setAmount(CartUtility.getAmountTotal(orderDto.getOrderDetail()));
		// orderDto.setTotalAmount(CartUtility.getAmountTotal(orderDto.getOrderDetail())
		// + 10.00);
		// orderDto.setDeliveryCharge(10.00);
		// orderDto.setOrderStatus("ORD1");
		// orderDto.setCustomerPhone(userDetail.getMobileNumber());
		/*
		 * if (!userDetail.getAddress().isEmpty()) { if
		 * (userDetail.getAddress().get(0).isHomeAddress()) {
		 * orderDto.setAddress(userDetail.getAddress().get(0)); } }
		 */
		CustomerAddress customerAddress = new CustomerAddress();
		for (Address addressDto : userDetail.getAddress()) {
			AddressInfo addressInfo = new AddressInfo();
			addressInfo.setAddressLine1(addressDto.getAddress1());
			addressInfo.setAddressLine2(addressDto.getAddress2());
			addressInfo.setCity(addressDto.getCity());
			addressInfo.setPincode(addressDto.getPincode());
			addressInfo.setHomeAddress(addressDto.isHomeAddress());
			addressInfo.setOfficeAddress(addressDto.isOfficeAddress());
			addressInfo.setId(addressDto.getId());
			if (addressDto.isHomeAddress()) {
				customerAddress.getHomeAddress().add(addressInfo);
			} else if (addressDto.isOfficeAddress()) {
				customerAddress.getOfficeAddress().add(addressInfo);
			} else {
				customerAddress.getOtherAddress().add(addressInfo);
			}
		}
		CheckOutResponse checkout = new CheckOutResponse();
		checkout.setAddresses(customerAddress);
		if (Utility.isEmpty(orderDto.getCustomerPhone())) {
			checkout.setMobilenumber(userDetail.getMobileNumber());
		} else {
			checkout.setMobilenumber(orderDto.getCustomerPhone());
		}

		// updateOrderDetails(orderDto, cartInfo);
		// orderRepository.save(orderDto);
		return checkout;
	}

	private void updateOrderDetails(Order orderDto, CartInfo cartInfo) {

		List<OrderDetail> orderDetailList = orderDto.getOrderDetail();
		if (Utility.isEmpty(orderDetailList)) {
			orderDetailList = new ArrayList<>();
		}
		List<CartLineInfo> cartLineList = cartInfo.getCartLines();

		for (CartLineInfo cartLine : cartLineList) {
			OrderDetail orderDetail = new OrderDetail();
			Product product = productService.getProduct(cartLine.getProductInfo().getId());
			orderDetail.setAmount(cartLine.getAmount());
			orderDetail.setQuanity(cartLine.getQuantity());
			orderDetail.setOrder(orderDto);
			orderDetail.setPrice(cartLine.getProductInfo().getPrice());
			orderDetail.setProduct(product);

			orderDetailList.add(orderDetail);
		}

	}

	@Override
	public void updateMobileNumber(HttpServletRequest request, UpdateMobileNumberRequest updateMobileNumberRequest) {

		Order order = orderRepository.findByReferenceId(updateMobileNumberRequest.getRefNumber());
		if (order == null || !updateMobileNumberRequest.isMobileNumberVerified()) {
			// throw some exception
		}

		order.setCustomerPhone(updateMobileNumberRequest.getMobileNumber());
		orderRepository.save(order);

	}

	@Override
	public void updateAddress(HttpServletRequest request, UpdateAddressRequest updateAddressRequest) {

		Address addressDto = null;
		AddressInfo addressInfo = updateAddressRequest.getAddressInfo();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByUserNameOrMobileNumber(userDetails.getUsername());
		Order orderDto = orderRepository.findByReferenceId(updateAddressRequest.getRefNumber());
		if (orderDto == null) {
			// throw some exception
		}
		orderDto.setCustomerName(updateAddressRequest.getCustomerName());
		orderDto.setEmail(updateAddressRequest.getCustomerEmail());
		if (!Utility.isEmpty(addressInfo.getId())) {
			addressDto = addressRepository.findById(addressInfo.getId()).get();
		}

		if (addressDto == null) {
			addressDto = new Address();
		}
		addressDto.setAddress1(addressInfo.getAddressLine1());
		addressDto.setAddress2(addressInfo.getAddressLine2());
		addressDto.setCity(addressInfo.getCity());
		addressDto.setPincode(addressInfo.getPincode());
		addressDto.setHomeAddress(addressInfo.isHomeAddress());
		addressDto.setOfficeAddress(addressDto.isOfficeAddress());
		addressDto.setUserDetail(user);
		orderDto.setAddress(addressDto);
		orderRepository.save(orderDto);
	}

	@Override
	public CustomerAddress getAddresses() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByUserNameOrMobileNumber(userDetails.getUsername());
		CustomerAddress customerAddress = new CustomerAddress();
		for (Address addressDto : user.getAddress()) {
			AddressInfo addressInfo = new AddressInfo();
			addressInfo.setAddressLine1(addressDto.getAddress1());
			addressInfo.setAddressLine2(addressDto.getAddress2());
			addressInfo.setCity(addressDto.getCity());
			addressInfo.setPincode(addressDto.getPincode());
			addressInfo.setHomeAddress(addressDto.isHomeAddress());
			addressInfo.setOfficeAddress(addressDto.isOfficeAddress());
			addressInfo.setId(addressDto.getId());
			if (addressDto.isHomeAddress()) {
				customerAddress.getHomeAddress().add(addressInfo);
			} else if (addressDto.isOfficeAddress()) {
				customerAddress.getOfficeAddress().add(addressInfo);
			} else {
				customerAddress.getOtherAddress().add(addressInfo);
			}
		}
		return customerAddress;
	}

	@Override
	public List<BookedProductDetail> placeOrder(HttpServletRequest request, String refNumber) throws Exception {
		Order order = orderRepository.findByReferenceId(refNumber);
		List<OrderDetail> orderDetailList = order.getOrderDetail();
		List<BookedProductDetail> bookedProductDetails = new ArrayList<>();
		if (order != null) {
			for (OrderDetail orderDetail : orderDetailList) {
				Product product = productService.getProduct(orderDetail.getProduct().getId());
				int updatedQuantity = product.getQuantity() - orderDetail.getQuanity();
				if (updatedQuantity < 0) {
					SingleObjectRequest productInfo = new SingleObjectRequest();
					productInfo.setId(product.getId());
					productInfo.setValue(product.getProductName());
					BookedProductDetail bookedProductDetail = new BookedProductDetail();
					bookedProductDetail.setShortFallQuantity(updatedQuantity);
					bookedProductDetail.setProductInfo(productInfo);
					bookedProductDetails.add(bookedProductDetail);
				}
			}
			if (Utility.isEmpty(bookedProductDetails)) {
				for (OrderDetail orderDetail : orderDetailList) {
					Product product = productService.getProduct(orderDetail.getProduct().getId());
					int updatedQuantity = product.getQuantity() - orderDetail.getQuanity();
					product.setQuantity(updatedQuantity);
					productRepository.save(product);
				}
				order.setOrderStatus("ORD1");
				order.setPaymentType("Cash on Delivery");
				if (Utility.isEmpty(order.getCustomerPhone())) {
					order.setCustomerPhone(order.getUserDetail().getMobileNumber());
				}
				orderRepository.save(order);
				otpService.generateSMS(order.getCustomerPhone(), order.getCustomerName(), refNumber);

			}
		}

		return bookedProductDetails;

	}

	@Override
	public GetOrdersResponse getAllOrders(Integer pageNo, Integer pageSize) {
		List<OrderInfo> allOrdersList = new ArrayList<>();
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("order_id").descending());
		Page<Order> orders = orderRepository.getAllOrdersByStatus("ORD6", paging);
		for (Order order : orders.getContent()) {
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setCustomerName(order.getCustomerName());
			orderInfo.setOrderId(order.getReferenceId());
			orderInfo.setCustomerPhone(order.getCustomerPhone());
			Address address = order.getAddress();
			StringBuilder addressBuilder = new StringBuilder();
			addressBuilder.append(address.getAddress1());
			addressBuilder.append(", ");
			addressBuilder.append(address.getAddress2());
			addressBuilder.append(", ");
			addressBuilder.append(address.getCity());
			addressBuilder.append(", ");
			addressBuilder.append(address.getPincode());
			orderInfo.setCustomerAddress(addressBuilder.toString());
			orderInfo.setOrderDate(order.getOrderDate().toString());
			orderInfo.setTotalAmount(order.getTotalAmount());
			orderInfo.setAmount(order.getAmount());
			orderInfo.setStatus(statusRepository.findByCode(order.getOrderStatus()));
			mapOrderDetails(order.getOrderDetail(), orderInfo.getOrderDetails());
			allOrdersList.add(orderInfo);

		}
		GetOrdersResponse getOrdersResponse = new GetOrdersResponse();
		getOrdersResponse.setOrders(allOrdersList);
		getOrdersResponse.setTotalPages(orders.getTotalPages());
		return getOrdersResponse;
	}

	private void mapOrderDetails(List<OrderDetail> orderDetailListDto, List<OrderDetailInfo> orderDetails) {
		for (OrderDetail orderDetailDto : orderDetailListDto) {
			OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
			orderDetailInfo.setAmount(orderDetailDto.getAmount());
			orderDetailInfo.setQuanity(orderDetailDto.getQuanity());
			orderDetailInfo.setPrice(orderDetailDto.getPrice());
			SingleObjectRequest productInfo = new SingleObjectRequest();
			productInfo.setId(orderDetailDto.getProduct().getId());
			productInfo.setValue(orderDetailDto.getProduct().getProductName());
			orderDetailInfo.setProductInfo(productInfo);
			orderDetails.add(orderDetailInfo);
		}
	}

	@Override
	public AdminData getData() {

		AdminData adminData = new AdminData();
		adminData.setPendingOrders(orderRepository.countByOrderStatus("ORD1"));
		adminData.setCancelledOrders(orderRepository.countByOrderStatus("ORD2"));
		adminData.setProcessedOrder(orderRepository.countByOrderStatus("ORD3"));
		adminData.setTotalIncome(orderRepository.getTotalIncome("ORD4").orElse(0.00));

		return adminData;
	}

	@Transactional
	@Override
	public void updateStatus(SingleObjectRequest request) {

		orderRepository.updateStatus(request.getCode(), request.getValue());
	}

	@Override
	public void updateDeliveryTime(HttpServletRequest request, DeliveryDateRequest deliveryDateRequest) {
		Order order = orderRepository.findByReferenceId(deliveryDateRequest.getRefNumber());
		if (order == null) {
			// throw some exception
		}
		LocalDateTime dateTime = null;
		if (deliveryDateRequest.isExpressDelivery()) {
			System.out.println("current date " + LocalDateTime.now());
			dateTime = LocalDateTime.now().plusMinutes(40);
			System.out.println("current date " + dateTime);
		} else {
			String deliveryDate = deliveryDateRequest.getDeliveryDate();
			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
			// HH:mm");
			dateTime = LocalDateTime.parse(deliveryDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		}

		order.setOrderDate(dateTime);
		orderRepository.save(order);
	}

	@Override
	public OrderInfo getOrderSummary(HttpServletRequest request, String refNumber) {
		Order order = orderRepository.findByReferenceId(refNumber);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCustomerName(order.getCustomerName());
		orderInfo.setOrderId(order.getReferenceId());
		orderInfo.setCustomerPhone(order.getCustomerPhone());
		Address address = order.getAddress();
		StringBuilder addressBuilder = new StringBuilder();
		addressBuilder.append(address.getAddress1());
		addressBuilder.append(", ");
		addressBuilder.append(address.getAddress2());
		addressBuilder.append(", ");
		addressBuilder.append(address.getCity());
		addressBuilder.append(", ");
		addressBuilder.append(address.getPincode());
		orderInfo.setCustomerAddress(addressBuilder.toString());
		orderInfo.setOrderDate(order.getOrderDate().toString());
		orderInfo.setTotalAmount(order.getTotalAmount());
		orderInfo.setAmount(order.getAmount());
		orderInfo.setStatus(statusRepository.findByCode(order.getOrderStatus()));
		mapOrderDetails(order.getOrderDetail(), orderInfo.getOrderDetails());

		return orderInfo;
	}

	@Override
	public List<OrderInfo> getMyOrders() {
		List<OrderInfo> allOrdersList = new ArrayList<>();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByUserNameOrMobileNumber(userDetails.getUsername());

		List<Order> myOrders = orderRepository.getOrderByUserId(user.getId());
		for (Order order : myOrders) {
			if (!order.getOrderStatus().equals("ORD6")) {

				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setCustomerName(order.getCustomerName());
				orderInfo.setOrderId(order.getReferenceId());
				orderInfo.setCustomerPhone(order.getCustomerPhone());
				Address address = order.getAddress();
				StringBuilder addressBuilder = new StringBuilder();
				addressBuilder.append(address.getAddress1());
				addressBuilder.append(", ");
				addressBuilder.append(address.getAddress2());
				addressBuilder.append(", ");
				addressBuilder.append(address.getCity());
				addressBuilder.append(", ");
				addressBuilder.append(address.getPincode());
				orderInfo.setCustomerAddress(addressBuilder.toString());
				orderInfo.setOrderDate(order.getOrderDate().toString());
				orderInfo.setTotalAmount(order.getTotalAmount());
				orderInfo.setAmount(order.getAmount());
				orderInfo.setStatus(statusRepository.findByCode(order.getOrderStatus()));
				mapOrderDetails(order.getOrderDetail(), orderInfo.getOrderDetails());
				allOrdersList.add(orderInfo);
			}
		}
		return allOrdersList;
	}

}
