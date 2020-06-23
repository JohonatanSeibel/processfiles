package com.watchservices.processfiles.model;

import java.math.BigDecimal;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class OrderItem {
	
	private Long id;
	private Integer quantity;
	private BigDecimal price;
	
	public OrderItem(Long id, Integer quantity, BigDecimal price) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getAmount() {
		return this.price.multiply(new BigDecimal(this.quantity));
	}
		
}
