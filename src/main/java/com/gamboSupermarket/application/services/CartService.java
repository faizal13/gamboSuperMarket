package com.gamboSupermarket.application.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gamboSupermarket.application.dto.Order;
import com.gamboSupermarket.application.dto.OrderDetail;
import com.gamboSupermarket.application.dto.Product;
import com.gamboSupermarket.application.dto.User;
import com.gamboSupermarket.application.model.AddToCartRequest;
import com.gamboSupermarket.application.model.CartInfoResponse;
import com.gamboSupermarket.application.model.CartLineInfo;
import com.gamboSupermarket.application.model.ProductInfo;
import com.gamboSupermarket.application.model.UpdateCartInfoRequest;
import com.gamboSupermarket.application.repository.OrderDetailRepository;
import com.gamboSupermarket.application.repository.OrderRepository;
import com.gamboSupermarket.application.repository.UserRepository;
import com.gamboSupermarket.application.utils.CartUtility;
import com.gamboSupermarket.application.utils.Utility;

@Service
public class CartService implements ICartService {

	private static final Logger logger = LoggerFactory.getLogger(CartService.class);

	@Autowired
	private IProductService productService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public String addToCart(AddToCartRequest addToCartRequest) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByUserNameOrMobileNumber(userDetails.getUsername());
		Product product = productService.getProduct(addToCartRequest.getProductData().getId());
		String refNumber = addToCartRequest.getRefNumber();
		// CartInfo cartInfo = null;
		Order order = null;
		if (product != null) {
			/*
			 * if (product.getQuantity() == 0) { // throw outOfStockException } else if
			 * (product.getQuantity() < addToCartRequest.getQuantity()) { // throw only
			 * limited stocks available exception }
			 */
			if (!Utility.isEmpty(refNumber)) {
				order = orderRepository.findByReferenceId(refNumber);
				// cartInfo = cartRepository.findById(refNumber).get();
			}
			if (order == null) {
				order = new Order();
				// cartInfo = new CartInfo();
				// refNumber = Long.valueOf(UUID.randomUUID().toString()).toString();
				refNumber = "BIG" + System.currentTimeMillis();
				order.setReferenceId(refNumber);
				order.setUserDetail(user);
				order.setOrderStatus("ORD6");
				// cartInfo.setOrderNum(refNumber);
				// cartInfo.setUserId(user.getId());

			}

			OrderDetail productOrder = new OrderDetail();
			productOrder.setAmount(product.getDiscountPrice() * addToCartRequest.getQuantity());
			productOrder.setOrder(order);
			productOrder.setPrice(product.getDiscountPrice());
			productOrder.setProduct(product);
			productOrder.setQuanity(addToCartRequest.getQuantity());
			orderDetailRepository.save(productOrder);

			order.getOrderDetail().add(productOrder);
			// ProductInfo productInfo = new ProductInfo(product);

			// cartInfo.addProduct(productInfo, addToCartRequest.getQuantity());
			// cartInfo.setUserId(user.getId());
			// cartInfo = cartRepository.save(cartInfo);
			order.setAmount(CartUtility.getAmountTotal(order.getOrderDetail()));
			order.setTotalAmount(CartUtility.getAmountTotal(order.getOrderDetail()) + 10.00);
			order.setDeliveryCharge(10.00);
			order = orderRepository.save(order);
		}
		// return cartInfo.getOrderNum();
		return order.getReferenceId();
	}

	/*
	 * @Override public String removeFromCart(AddToCartRequest addToCartRequest) {
	 * 
	 * String refNumber = addToCartRequest.getRefNumber(); CartInfo cartInfo =
	 * cartRepository.findById(refNumber).get(); if (cartInfo == null) { // throw
	 * cartNotFoundException } Product product =
	 * productService.getProduct(addToCartRequest.getProductData().getId()); if
	 * (product != null) {
	 * 
	 * ProductInfo productInfo = new ProductInfo(product);
	 * cartInfo.removeProduct(productInfo); cartRepository.save(cartInfo); } if
	 * (cartInfo.isEmpty()) { cartRepository.deleteById(refNumber); } return
	 * refNumber; }
	 */

	@Transactional
	@Override
	public String updateCart(UpdateCartInfoRequest updateCartInfoRequest) {
		if (Utility.isEmpty(updateCartInfoRequest.getCartList())) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userRepository.findByUserNameOrMobileNumber(userDetails.getUsername());
			Order order = orderRepository.getOrderByUserIdAndStatus(user.getId(), "ORD6");
			orderDetailRepository.deleteCartLine(order.getId());
			// orderRepository.delete(order);
			// orderRepository.deleteCart(user.getId(), "ORD6");
			return "cart items removed";
		} else {
			Order order = orderRepository
					.findByReferenceIdAndOrderStatus(updateCartInfoRequest.getCartList().get(0).getRefNumber(), "ORD6");
			// CartInfo cartInfo =
			// cartRepository.findById(updateCartInfoRequest.getCartList().get(0).getRefNumber()).get();

			if (order != null) {
				orderDetailRepository.deleteCartLine(order.getId());
				List<OrderDetail> orderDetails = order.getOrderDetail();
				orderDetails = new ArrayList<>();
				for (AddToCartRequest cartProduct : updateCartInfoRequest.getCartList()) {

					Product product = productService.getProduct(cartProduct.getProductData().getId());
					if (product != null) {
						OrderDetail productOrder = new OrderDetail();
						productOrder.setAmount(product.getDiscountPrice() * cartProduct.getQuantity());
						productOrder.setOrder(order);
						productOrder.setPrice(product.getDiscountPrice());
						productOrder.setProduct(product);
						productOrder.setQuanity(cartProduct.getQuantity());

						orderDetails.add(productOrder);
						/*
						 * ProductInfo productInfo = new ProductInfo(product); for (CartLineInfo
						 * cartline : cartInfo.getCartLines()) { if
						 * (productInfo.equals(cartline.getProductInfo())) {
						 * cartline.setQuantity(cartProduct.getQuantity()); continue; } }
						 */
					} else {
						// throw some exception
					}
				}
				// cartInfo.updateQuantity(cartInfo);
				orderDetailRepository.saveAll(orderDetails);
			}
			order.setAmount(CartUtility.getAmountTotal(order.getOrderDetail()));
			order.setTotalAmount(CartUtility.getAmountTotal(order.getOrderDetail()) + 10.00);
			order.setDeliveryCharge(10.00);
			orderRepository.save(order);
			// cartRepository.save(cartInfo);
			return updateCartInfoRequest.getCartList().get(0).getRefNumber();
		}
	}

	@Override
	public CartInfoResponse getCartInfo(String refnumber) {
		// CartInfo cartInfo = cartRepository.findById(refnumber).get();
		Order order = orderRepository.findByReferenceIdAndOrderStatus(refnumber, "ORD6");
		List<CartLineInfo> cartLines = new ArrayList<>();
		CartInfoResponse cartInfoResponse = null;
		if (order != null) {
			cartInfoResponse = new CartInfoResponse();
			cartInfoResponse.setAmount(CartUtility.getAmountTotal(order.getOrderDetail()));
			cartInfoResponse.setTotalAmount(
					CartUtility.getAmountTotal(order.getOrderDetail()) + cartInfoResponse.getDeliveryCharge());
			cartInfoResponse.setRefNumber(order.getReferenceId());
			cartInfoResponse.setTotalSaving(CartUtility.getSavingAmount(order.getOrderDetail()));
			for (OrderDetail orderDetail : order.getOrderDetail()) {
				CartLineInfo cartLine = new CartLineInfo();
				ProductInfo productInfo = new ProductInfo(orderDetail.getProduct());
				cartLine.setProductInfo(productInfo);
				cartLine.setQuantity(orderDetail.getQuanity());
				cartLines.add(cartLine);
			}

			cartInfoResponse.setCartLines(cartLines);
		}

		return cartInfoResponse;
	}

	@Override
	public String removeFromCart(AddToCartRequest addToCartRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
