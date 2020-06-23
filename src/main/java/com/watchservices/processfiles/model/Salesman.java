package com.watchservices.processfiles.model;

import java.math.BigDecimal;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Salesman {

	private String cpf;
	private String name;
	private BigDecimal salary;
	
	
	public Salesman(String cpf, String name, BigDecimal salary) {
		super();
		this.cpf = cpf;
		this.name = name;
		this.salary = salary;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Salesman other = (Salesman) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
	
}
