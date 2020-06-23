package com.watchservices.processfiles.services.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.watchservices.processfiles.model.Salesman;
import com.watchservices.processfiles.services.SalesmanService;

@Service
public class SalesmanServiceImpl implements SalesmanService {
	
	@Override
	public Salesman getSalesmanByLine(String line) {
		String cpf;
		String name;
		BigDecimal salary;
		try {
			String[] data = line.split("ç");
			cpf = data[1];
			name = data[2];
			salary = new BigDecimal(data[3]);
		} catch (Exception e) {
			return null;
		}
		return new Salesman(cpf, name, salary);
	}
}
