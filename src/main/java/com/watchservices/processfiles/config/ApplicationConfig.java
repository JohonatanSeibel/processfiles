package com.watchservices.processfiles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.watchservices.processfiles.services.ClientService;
import com.watchservices.processfiles.services.OrderService;
import com.watchservices.processfiles.services.ProcessFileService;
import com.watchservices.processfiles.services.ResultService;
import com.watchservices.processfiles.services.SalesmanService;
import com.watchservices.processfiles.services.impl.ClientServiceImpl;
import com.watchservices.processfiles.services.impl.OrderServiceImpl;
import com.watchservices.processfiles.services.impl.ProcessFileServiceImpl;
import com.watchservices.processfiles.services.impl.ResultServiceImpl;
import com.watchservices.processfiles.services.impl.SalesmanServiceImpl;

@Configuration
public class ApplicationConfig {

	@Bean
	public ProcessFileService processFileService() {
		ProcessFileService processFileService = new ProcessFileServiceImpl();
		return processFileService;
	}
	
	@Bean
	public ClientService clientService() {
		ClientService clientService = new ClientServiceImpl();
		return clientService;
	}
	
	@Bean
	public SalesmanService salesmanService() {
		SalesmanService salesmanService = new SalesmanServiceImpl();
		return salesmanService;
	}
	
	@Bean
	public OrderService orderService() {
		OrderService orderService = new OrderServiceImpl();
		return orderService;
	}
	
	@Bean
	public ResultService resultService() {
		ResultService orderService = new ResultServiceImpl();
		return orderService;
	}
}
