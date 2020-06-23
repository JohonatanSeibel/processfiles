package com.watchservices.processfiles.services;

import com.watchservices.processfiles.model.Client;

public interface ClientService {

	Client getClientByLine(String line);

}
