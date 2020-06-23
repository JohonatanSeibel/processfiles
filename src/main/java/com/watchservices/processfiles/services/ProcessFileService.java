package com.watchservices.processfiles.services;

import java.io.IOException;

public interface ProcessFileService {
	void scanDirectoryChanges() throws IOException ;
	void launcher()  throws IOException ;
}
