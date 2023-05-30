package com.gamboSupermarket.application.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamboSupermarket.application.model.AddProductRequest;
import com.gamboSupermarket.application.model.AllProductResponse;
import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.EmptyJsonResponse;
import com.gamboSupermarket.application.model.ProductCategoryResponse;
import com.gamboSupermarket.application.model.ProductDetailResponse;
import com.gamboSupermarket.application.model.ProductRequest;
import com.gamboSupermarket.application.model.SingleObjectRequest;
import com.gamboSupermarket.application.services.IProductService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(path = "/api")
public class ProductsController {

	@Autowired
	private IProductService productService;

	@PostMapping(value = "/products/addProduct")
	public ResponseEntity<CustomResponse<Object>> addProduct(@ModelAttribute AddProductRequest addProductRequest)
			throws IOException {

		productService.addProduct(addProductRequest);
		CustomResponse<Object> response = new CustomResponse<>(200, "Product added successfully",
				new EmptyJsonResponse());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = { "/auth/products/getProducts" })
	public ResponseEntity<CustomResponse<AllProductResponse>> getAllProducts() throws IOException {
		AllProductResponse allProductResponse = productService.getAllProducts();
		CustomResponse<AllProductResponse> response = new CustomResponse<>(200, "Success", allProductResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(path = { "/auth/products/getProductsByCategory" })
	public ResponseEntity<CustomResponse<ProductCategoryResponse>> getProductsByCategory(HttpServletRequest request,
			@RequestBody ProductRequest productRequest) throws IOException {
		ProductCategoryResponse productCategoryResponse = productService.getProductsByCategory(productRequest);
		CustomResponse<ProductCategoryResponse> response = new CustomResponse<>(200, "Success", productCategoryResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/auth/products/productDetail")
	public ResponseEntity<CustomResponse<ProductDetailResponse>> getProductDetail(
			@RequestBody SingleObjectRequest productDetailRequest) throws IOException {
		ProductDetailResponse productDetailResponse = productService.getProductDetail(productDetailRequest.getId());
		CustomResponse<ProductDetailResponse> response = new CustomResponse<>(200, "success", productDetailResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/*
	 * @PostMapping(value = "/addToCart") public
	 * ResponseEntity<CustomResponse<Object>> addToCart(HttpServletRequest request,
	 * 
	 * @RequestBody AddToCartRequest addToCartRequest) throws IOException {
	 * 
	 * // UserDetails userDetails = (UserDetails) //
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //
	 * User user = //
	 * userRepository.findByUserNameOrMobileNumber(userDetails.getUsername());
	 * Product product =
	 * productService.getProduct(addToCartRequest.getProductData().getId()); String
	 * refNumber = null; if (product != null) {
	 * 
	 * // CartInfo cartInfo = CartUtility.getCartInSession(request, //
	 * addToCartRequest.getRefNumber()); CartInfo cartInfo = null;
	 * 
	 * if (refNumber != null) {
	 * 
	 * cartInfo = (CartInfo) cartRepository.save(cartInfo); }
	 * 
	 * 
	 * if (cartInfo == null) { cartInfo = new CartInfo(); refNumber =
	 * UUID.randomUUID().toString(); cartInfo.setOrderNum(refNumber);
	 * 
	 * // request.getSession().setAttribute(refNumber, cartInfo); }
	 * 
	 * ProductInfo productInfo = new ProductInfo(product);
	 * 
	 * cartInfo.addProduct(productInfo, addToCartRequest.getQuantity()); //
	 * cartInfo.setUserId(user.getId()); cartInfo = (CartInfo)
	 * cartRepository.save(cartInfo); refNumber = cartInfo.getOrderNum(); }
	 * CustomResponse<Object> response = new CustomResponse<>(200, "Success",
	 * refNumber); return new ResponseEntity<>(response, HttpStatus.OK); }
	 * 
	 * @PostMapping(value = "/removeFromCart") public
	 * ResponseEntity<CustomResponse<Object>> removeFromCart(HttpServletRequest
	 * request,
	 * 
	 * @RequestBody AddToCartRequest addToCartRequest) throws IOException {
	 * 
	 * String refNumber = null; Product product =
	 * productService.getProduct(addToCartRequest.getProductData().getId()); if
	 * (product != null) {
	 * 
	 * CartInfo cartInfo = CartUtility.getCartInSession(request,
	 * addToCartRequest.getRefNumber());
	 * 
	 * ProductInfo productInfo = new ProductInfo(product);
	 * 
	 * cartInfo.removeProduct(productInfo); refNumber = cartInfo.getOrderNum(); }
	 * CustomResponse<Object> response = new CustomResponse<>(200, "Success",
	 * refNumber); return new ResponseEntity<>(response, HttpStatus.OK); }
	 * 
	 * @PostMapping(value = "/updateQuantityInCart") public
	 * ResponseEntity<CustomResponse<Object>>
	 * updateQuantityInCart(HttpServletRequest request,
	 * 
	 * @RequestBody UpdateCartInfoRequest updateCartInfoRequest) throws IOException
	 * {
	 * 
	 * CartInfo cartInfo = CartUtility.getCartInSession(request,
	 * updateCartInfoRequest.getCartList().get(0).getRefNumber()); for
	 * (AddToCartRequest cartProduct : updateCartInfoRequest.getCartList()) {
	 * 
	 * Product product =
	 * productService.getProduct(cartProduct.getProductData().getId()); if (product
	 * != null) { ProductInfo productInfo = new ProductInfo(product); for
	 * (CartLineInfo cartline : cartInfo.getCartLines()) { if
	 * (productInfo.equals(cartline.getProductInfo())) {
	 * cartline.setQuantity(cartProduct.getQuantity()); continue; } } } }
	 * cartInfo.updateQuantity(cartInfo); CustomResponse<Object> response = new
	 * CustomResponse<>(200, "Success",
	 * updateCartInfoRequest.getCartList().get(0).getRefNumber()); return new
	 * ResponseEntity<>(response, HttpStatus.OK); }
	 * 
	 * @GetMapping(path = { "/getCartInfo" }) public
	 * ResponseEntity<CustomResponse<CartInfoResponse>> getCartInfo(@RequestParam
	 * String refnumber, HttpServletRequest request) throws IOException { CartInfo
	 * cartInfo = CartUtility.getCartInSession(request, refnumber); CartInfoResponse
	 * cartInfoResponse = new CartInfoResponse();
	 * cartInfoResponse.setAmount(cartInfo.getAmountTotal());
	 * cartInfoResponse.setTotalAmount(cartInfo.getAmountTotal() +
	 * cartInfoResponse.getDeliveryCharge());
	 * cartInfoResponse.setRefNumber(cartInfo.getOrderNum());
	 * cartInfoResponse.setTotalSaving(cartInfo.getSavingAmount());
	 * cartInfoResponse.setCartLines(cartInfo.getCartLines());
	 * CustomResponse<CartInfoResponse> response = new CustomResponse<>(200,
	 * "Success", cartInfoResponse); return new ResponseEntity<>(response,
	 * HttpStatus.OK);
	 * 
	 * }
	 */

}
