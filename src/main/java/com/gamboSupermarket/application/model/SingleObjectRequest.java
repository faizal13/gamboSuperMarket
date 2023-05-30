package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class SingleObjectRequest {
	
	private Long id;
	
	private String code;
	
	private String value;

}
