package com.gamboSupermarket.application.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="orders")
public class Order extends BaseModel{
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;
	
	@Column(name="reference_number")
    private String referenceId;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userDetail;
	
	@Column(name = "Order_Date")
    private LocalDateTime orderDate;
	
	@Column(name="order_status")
    private String orderStatus;
	
	@Column(name="amount")
    private double amount;
	
	@Column(name="delivery_charge")
    private double deliveryCharge;
	
	@Column(name="total_amount")
    private double totalAmount;
	
	@Column(name="customer_name")
    private String customerName;
	
	@Column(name="email")
    private String email;
	
	@Column(name="payment_type")
    private String paymentType;
	
	@Column(name = "customer_phone", length = 128)
    private String customerPhone;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;
	
	@OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetail = new ArrayList<>();
	
	
	
}
