package com.watchservices.processfiles.services.impl;

import org.springframework.stereotype.Service;

import com.watchservices.processfiles.model.Client;
import com.watchservices.processfiles.services.ClientService;

@Service
public class ClientServiceImpl implements ClientService{

	@Override
	public Client getClientByLine(String line) {
		String name;
		String cnpj;
		String businessArea;
		try {
			String[] data = line.split("ç");
			cnpj = data[1];
			name = data[2];
			businessArea = data[3];
		} catch (Exception e) {
			return null;
		}
		return new Client(cnpj, name,  businessArea);
	}
}
