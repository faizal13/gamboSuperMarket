package com.gamboSupermarket.application.services;

import com.gamboSupermarket.application.model.AddToCartRequest;
import com.gamboSupermarket.application.model.CartInfoResponse;
import com.gamboSupermarket.application.model.UpdateCartInfoRequest;

public interface ICartService {
	
   public String addToCart(AddToCartRequest addToCartRequest);
   
   public String removeFromCart(AddToCartRequest addToCartRequest);
   
   public String updateCart(UpdateCartInfoRequest updateCartInfoRequest);
   
   public CartInfoResponse getCartInfo(String refnumber);
}
