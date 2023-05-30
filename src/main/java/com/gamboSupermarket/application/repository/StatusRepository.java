package com.gamboSupermarket.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamboSupermarket.application.dto.OtpDetails;
import com.gamboSupermarket.application.dto.StatusMaster;


public interface StatusRepository extends JpaRepository<StatusMaster, Long>  {
	
	StatusMaster findByCode(String code);
	
	
}
