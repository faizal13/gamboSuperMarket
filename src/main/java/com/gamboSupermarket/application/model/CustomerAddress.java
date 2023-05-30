package com.gamboSupermarket.application.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CustomerAddress {

	private List<AddressInfo> homeAddress = new ArrayList<>();
	private List<AddressInfo> officeAddress = new ArrayList<>();
	private List<AddressInfo> otherAddress = new ArrayList<>();

}
