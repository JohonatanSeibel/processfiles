package com.watchservices.processfiles.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.watchservices.processfiles.model.Order;
import com.watchservices.processfiles.model.OrderItem;
import com.watchservices.processfiles.model.Salesman;
import com.watchservices.processfiles.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Override
	public Order getOrderByLine(String line, Salesman salesman) {
		Long id;
		List<OrderItem> orderItem = new ArrayList<OrderItem>();
		try {
			String[] data = line.split("ç");
			id = Long.valueOf(data[1]);
			for (String str2 : data[2].replace("[", "").replace("]", "").split(",")) {
				orderItem.add(getOrderItens(str2));
			}
		} catch (Exception e) {
			return null;
		}
		return new Order(id, orderItem, salesman);
	}
	
	private OrderItem getOrderItens(String item) {
		Long id;
		Integer quantity;
		BigDecimal price;
		try {
			String[] quebra = item.split("-");
			id = Long.valueOf(quebra[0]);
			quantity = Integer.valueOf(quebra[1]);
			price = new BigDecimal(quebra[2]);
		} catch (Exception e) {
			return null;
		}
		return new OrderItem(id, quantity, price);
	}
}
