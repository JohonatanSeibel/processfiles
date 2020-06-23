package com.watchservices.processfiles;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.watchservices.processfiles.services.ProcessFileService;

@SpringBootApplication
public class ProcessfilesApplication {
	
	@Autowired
	private ProcessFileService processFileService;

	public static void main(String[] args) {
		SpringApplication.run(ProcessfilesApplication.class, args);
	}

	@PostConstruct
	public void init() {
		try {
			processFileService.launcher();
			processFileService.scanDirectoryChanges();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
