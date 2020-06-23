package com.watchservices.processfiles.services.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;

import com.watchservices.processfiles.model.Order;
import com.watchservices.processfiles.services.ProcessFileService;
import com.watchservices.processfiles.services.ResultService;

import ch.qos.logback.classic.Logger;

public class ResultServiceImpl implements ResultService{
	
	private static final String PATH_OUT = System.getenv("HOME") + "/data/out/";
	private static final Logger LOG = (Logger) LoggerFactory.getLogger(ProcessFileService.class);
	private String lessProductiveSaller;
	private Long mostExpresiveSale;
	
	@Override
	public void processResultSale(int quantitySellers, List<Order> listOrder, int quantityClients) {
		Map<String, BigDecimal> mapSallers = new HashMap<String, BigDecimal>();
		Map<Long, BigDecimal> mapOrder = new HashMap<Long, BigDecimal>();
		for (Order order : listOrder) {
			BigDecimal amount = order.getAmount();
			if (mapSallers.containsKey(order.getSalesman().getName())) {
				amount = mapSallers.get(order.getSalesman().getName());
			}
			mapSallers.put(order.getSalesman().getName(), amount.add(amount));
			if (mapOrder.containsKey(order.getId())) {
				amount = mapOrder.get(order.getId());
			}
			mapOrder.put(order.getId(), amount.add(amount));
			
		}
		Map<String, BigDecimal> resultSallers = mapSallers.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.naturalOrder())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
		Map<Long, BigDecimal> resultOrder = mapOrder.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		if (!resultOrder.isEmpty()) {
			if(resultSallers.size() > 1) 
				this.setLessProductiveSaller(resultSallers.keySet().iterator().next());
			
			this.setMostExpresiveSale(resultOrder.keySet().iterator().next());
			this.writeReturn(quantitySellers, quantityClients);
		}
	}

	private void writeReturn(int quantitySellers, int quantityClients) {
		try {
			cleanDirectory();
			SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyyHHmmss");
			String date = formatDate.format(new Date());
			FileWriter fileWriter = new FileWriter(PATH_OUT + date + ".done.dat");

			fileWriter.write("Quantidade de clientes no arquivo de entrada: " + quantityClients + "\r\n"
					+ "Quantidade de vendedor no arquivo de entrada: " + quantitySellers + "\r\n"
					+ "ID da venda mais cara: " + this.getMostExpresiveSale() + "\r\n" + "Pior Vendedor: "
					+ this.getLessProductiveSaller());
			fileWriter.close();
			LOG.info("Successfully created file: " + date + ".done.dat");
		} catch (IOException e) {
			LOG.warn("There is a problem with the Output directory, please check!");
			e.printStackTrace();
		}
	}
	
	private void cleanDirectory() {
		File folder = new File(PATH_OUT);
		if (folder.isDirectory()) {
			File[] sun = folder.listFiles();
			for (File toDelete : sun) {
				toDelete.delete();
			}
		}
	}

	public String getLessProductiveSaller() {
		return lessProductiveSaller;
	}

	public void setLessProductiveSaller(String lessProductiveSaller) {
		this.lessProductiveSaller = lessProductiveSaller;
	}

	public Long getMostExpresiveSale() {
		return mostExpresiveSale;
	}

	public void setMostExpresiveSale(Long mostExpresiveSale) {
		this.mostExpresiveSale = mostExpresiveSale;
	}
		
}
