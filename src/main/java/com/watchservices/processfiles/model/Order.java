package com.watchservices.processfiles.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Order {
	
	private Long id;
	private List<OrderItem> itens;
	private Salesman salesman;
	
	public Order(Long id, List<OrderItem> itens, Salesman salesman) {
		this.id = id;
		this.itens = itens;
		this.salesman = salesman;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<OrderItem> getItens() {
		return itens;
	}
	public void setItens(List<OrderItem> itens) {
		this.itens = itens;
	}
	public Salesman getSalesman() {
		return salesman;
	}
	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}
	
	public BigDecimal getAmount() {
		return itens.stream().map(OrderItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
}
