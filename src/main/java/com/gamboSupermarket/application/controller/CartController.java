package com.gamboSupermarket.application.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamboSupermarket.application.dto.Product;
import com.gamboSupermarket.application.dto.User;
import com.gamboSupermarket.application.model.AddToCartRequest;
import com.gamboSupermarket.application.model.CartInfo;
import com.gamboSupermarket.application.model.CartInfoResponse;
import com.gamboSupermarket.application.model.CartLineInfo;
import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.ProductInfo;
import com.gamboSupermarket.application.model.UpdateCartInfoRequest;
import com.gamboSupermarket.application.repository.CartRepository;
import com.gamboSupermarket.application.repository.UserRepository;
import com.gamboSupermarket.application.services.ICartService;
import com.gamboSupermarket.application.services.IProductService;
import com.gamboSupermarket.application.utils.CartUtility;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(path = "/api/cart")
public class CartController {

	@Autowired
	private ICartService cartService;

	@PostMapping(value = "/addToCart")
	public ResponseEntity<CustomResponse<Object>> addToCart(HttpServletRequest request,
			@RequestBody AddToCartRequest addToCartRequest) throws IOException {
		String refNumber = cartService.addToCart(addToCartRequest);
		CustomResponse<Object> response = new CustomResponse<>(200, "Success", refNumber);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/removeFromCart")
	public ResponseEntity<CustomResponse<Object>> removeFromCart(HttpServletRequest request,
			@RequestBody AddToCartRequest addToCartRequest) throws IOException {
		String refNumber = cartService.removeFromCart(addToCartRequest);
		CustomResponse<Object> response = new CustomResponse<>(200, "Success", refNumber);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/updateQuantityInCart")
	public ResponseEntity<CustomResponse<Object>> updateQuantityInCart(HttpServletRequest request,
			@RequestBody UpdateCartInfoRequest updateCartInfoRequest) throws IOException {
		String refNumber = cartService.updateCart(updateCartInfoRequest);
		CustomResponse<Object> response = new CustomResponse<>(200, "Success", refNumber);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = { "/getCartInfo" })
	public ResponseEntity<CustomResponse<CartInfoResponse>> getCartInfo(@RequestParam String refnumber,
			HttpServletRequest request) throws IOException {
		CartInfoResponse cartInfoResponse = cartService.getCartInfo(refnumber);
		CustomResponse<CartInfoResponse> response = new CustomResponse<>(200, "Success", cartInfoResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
