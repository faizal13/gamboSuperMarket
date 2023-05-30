package com.gamboSupermarket.application.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "otp_details")
public class OtpDetails extends BaseModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "status")
	private String status;

	@Column(name = "txn_id")
	private String txnId;

}
