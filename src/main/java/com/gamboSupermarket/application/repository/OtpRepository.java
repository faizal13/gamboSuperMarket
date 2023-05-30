package com.gamboSupermarket.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamboSupermarket.application.dto.OtpDetails;


public interface OtpRepository extends JpaRepository<OtpDetails, Long>  {
	
	OtpDetails findByMobileNumber(String mobileNumber);
	
	Boolean existsByMobileNumber(String mobileNumber);
}
