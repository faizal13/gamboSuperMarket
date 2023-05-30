package com.gamboSupermarket.application.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="address")
public class Address {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private Long id;
	
	@Column(name="address_line1")
    private String address1;
	
	@Column(name="address_line2")
    private String address2;
	
	@Column(name="city")
    private String city;
	
	@Column(name="pincode")
    private String pincode;
	
	@Column(name="is_home_address")
    private boolean homeAddress = false;
	
	@Column(name="is_office_address")
    private boolean officeAddress = false;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userDetail;
	
	@Column(name="longitude")
    private String longitude;

    @Column(name="latitude")
    private String latitude;

}
