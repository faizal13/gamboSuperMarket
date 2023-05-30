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
@Table(name = "product_images")
public class ProductImages {

	@Id
	@Column(name = "product_id")
	private Long id;

	@Lob
	@Column(name = "image1", length = Integer.MAX_VALUE)
	private byte[] productImageByte1;
	
	@Lob
	@Column(name = "image2", length = Integer.MAX_VALUE)
	private byte[] productImageByte2;
	
	@Lob
	@Column(name = "image3", length = Integer.MAX_VALUE)
	private byte[] productImageByte3;


}
