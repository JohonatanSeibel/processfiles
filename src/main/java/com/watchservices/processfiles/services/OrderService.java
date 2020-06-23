package com.watchservices.processfiles.services;

import com.watchservices.processfiles.model.Order;
import com.watchservices.processfiles.model.Salesman;

public interface OrderService {

	Order getOrderByLine(String line, Salesman salesman);

}
