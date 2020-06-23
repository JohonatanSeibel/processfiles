package com.watchservices.processfiles.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watchservices.processfiles.model.Client;
import com.watchservices.processfiles.model.Order;
import com.watchservices.processfiles.model.Salesman;
import com.watchservices.processfiles.services.ClientService;
import com.watchservices.processfiles.services.OrderService;
import com.watchservices.processfiles.services.ProcessFileService;
import com.watchservices.processfiles.services.ResultService;
import com.watchservices.processfiles.services.SalesmanService;

import ch.qos.logback.classic.Logger;

@Service
public class ProcessFileServiceImpl implements ProcessFileService {

	@Autowired
	private ClientService clientService;

	@Autowired
	private SalesmanService salesmanService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ResultService resultService;

	private static final Logger LOG = (Logger) LoggerFactory.getLogger(ProcessFileService.class);

	private static final String PATH_IN = System.getenv("HOMEPATH") + "/data/in/";
	private static final String PATH_OUT = System.getenv("HOMEPATH") + "/data/out/";
	private List<Client> listClient;
	private List<Salesman> listSalesman;
	private List<Order> listOrder;

	@Override
	public void launcher() throws IOException {
		this.listClient = new ArrayList<Client>();
		this.listSalesman = new ArrayList<Salesman>();
		this.listOrder = new ArrayList<Order>();
		File folder = new File(PATH_IN);
		try {
			this.queryDirectoryFiles(folder);
		} catch (Exception e) {
			LOG.warn("There is a problem with the input directory, please check");
		}
	}

	@Override
	public void scanDirectoryChanges() throws IOException {
		try {
			WatchService service = FileSystems.getDefault().newWatchService();
			Path pathIn = Paths.get(PATH_IN);
			pathIn.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);
			WatchKey key = null;
			while ((key = service.take()) != null) {
				File folder = new File(PATH_IN);
				key.pollEvents();
				this.queryDirectoryFiles(folder);
				key.reset();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void queryDirectoryFiles(File folder) {
		this.setListCliente(new ArrayList<Client>());
		this.setListSalesman(new ArrayList<Salesman>());
		this.setListOrder(new ArrayList<Order>());
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				this.queryDirectoryFiles(fileEntry);
			} else {
				if (fileEntry.getName().toString().endsWith(".dat")
						|| fileEntry.getName().toString().endsWith(".DAT")) {
					loadData(fileEntry.getName());
				}
			}
		}
		if (folder.listFiles().length != 0) {
			resultService.processResultSale(this.getListSalesman().size(), this.getListOrder(),
					this.getListClient().size());
		} else {
			cleanDirectoryOut();
		}
	}

	private void cleanDirectoryOut() {
		File folder = new File(PATH_OUT);
		if (folder.isDirectory()) {
			File[] sun = folder.listFiles();
			for (File toDelete : sun) {
				toDelete.delete();
			}
		}
	}

	public void loadData(String file) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(PATH_IN + file));
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("001ç")) {
					if (!findSalesman(null, getDataByIndex(line, 1)).isPresent()) {
						this.getListSalesman().add(this.salesmanService.getSalesmanByLine(line));
					} else {
						LOG.warn("Salesman with this cpf(" + getDataByIndex(line, 1) + ") already exists!");
					}
				} else if (line.contains("002ç")) {
					if (!findClient(getDataByIndex(line, 1)).isPresent()) {
						this.getListClient().add(this.clientService.getClientByLine(line));
					} else {
						LOG.warn("Client with this cnpj(" + getDataByIndex(line, 1) + ") already exists!");
					}
				} else if (line.contains("003ç")) {
					if (!findOrder(Long.valueOf(getDataByIndex(line, 1))).isPresent()) {
						String salesmanName = getDataByIndex(line, 3);
						Salesman salesman;
						try {
							salesman = this.findSalesman(salesmanName, null).get();
						} catch (Exception e) {
							salesman = null;
							LOG.info("The Salesman " + salesmanName + " don't exist!");
							continue;
						}
						Order order = orderService.getOrderByLine(line, salesman);
						this.getListOrder().add(order);
					} else {
						LOG.warn("Order with this ID(" + getDataByIndex(line, 1) + ") already exists!");
					}
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			LOG.warn("Error to load files!");
		}
	}

	private String getDataByIndex(String line, int index) {
		String[] data = line.split("ç");
		return data[index];
	}

	private final Optional<Client> findClient(String clientCnpj) {
		return getListClient().stream().filter(c -> c.getCnpj().equals(clientCnpj)).findAny();
	}

	private final Optional<Salesman> findSalesman(String salesmanName, String cpf) {
		if (cpf != null)
			return getListSalesman().stream().filter(s -> s.getCpf().equals(cpf)).findAny();

		return getListSalesman().stream().filter(s -> s.getName().equals(salesmanName)).findAny();
	}

	private final Optional<Order> findOrder(Long OrderId) {
		return getListOrder().stream().filter(c -> c.getId().equals(OrderId)).findAny();
	}

	public List<Client> getListClient() {
		return listClient;
	}

	public void setListCliente(List<Client> listClient) {
		this.listClient = listClient;
	}

	public List<Salesman> getListSalesman() {
		return listSalesman;
	}

	public void setListSalesman(List<Salesman> listSalesman) {
		this.listSalesman = listSalesman;
	}

	public List<Order> getListOrder() {
		return listOrder;
	}

	public void setListOrder(List<Order> listOrder) {
		this.listOrder = listOrder;
	}
}
