package com.gamboSupermarket.application.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gamboSupermarket.application.utils.CartUtility;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String productName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Categories category;

	@Column(name = "price")
	private double price;

	@Column(name = "discount_price")
	private double discountPrice;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "status")
	private boolean status;

	@Lob
	@Column(name = "image", length = Integer.MAX_VALUE)
	private byte[] productImageByte;

	@Column(name = "description")
	private String description;

	@Column(name = "featured")
	private boolean featured = false;

	@Column(name = "percent_discount")
	private float percentDiscount;

}
