package com.watchservices.processfiles.services;

import java.util.List;

import com.watchservices.processfiles.model.Order;

public interface ResultService {

	void processResultSale(int quantitySellers, List<Order> listOrder, int quantityClients);

}
