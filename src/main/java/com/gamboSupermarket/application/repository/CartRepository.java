package com.gamboSupermarket.application.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gamboSupermarket.application.model.CartInfo;

@Transactional
public interface CartRepository extends MongoRepository<CartInfo, String> {
	

}
