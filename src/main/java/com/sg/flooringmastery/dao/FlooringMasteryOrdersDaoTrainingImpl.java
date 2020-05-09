/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryOrdersDaoTrainingImpl implements FlooringMasteryOrdersDao {
    
    public static final String ORDERS_PATH = "orders";
    public static final String DELIMITER = ",";
    protected Map<Integer, Order> orders = new HashMap<>();
    
    @Override
    public void loadOrders() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        List<String> orderFileList = getOrderFileList();
        
        for (String currentOrderFile : orderFileList) {
            try {
                // Create Scanner for reading the order file
                scanner = new Scanner(new BufferedReader(new FileReader(currentOrderFile)));
            } catch (FileNotFoundException e) {
                throw new FlooringMasteryPersistenceException("Could not load order data into memory.", e);
            }

            String currentLine;
            String[] currentTokens;

            // Loop through all the lines in the order file
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                // Break line into tokens
                currentTokens = currentLine.split(DELIMITER);
                // Create order based on tokens
                int orderNumber = Integer.parseInt(currentTokens[0]);
                String dateString = currentOrderFile.split("_")[1].split(".txt")[0];
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMddyyyy"));
                Order currentOrder = new Order(orderNumber);
                currentOrder.setDate(date);
                currentOrder.setCustomerName(currentTokens[1]);
                currentOrder.setState(currentTokens[2]);
                currentOrder.setTaxRate(new BigDecimal(currentTokens[3]));
                currentOrder.setProductType(currentTokens[4]);
                currentOrder.setArea(new BigDecimal(currentTokens[5]));
                currentOrder.setCostPerSquareFoot(new BigDecimal(currentTokens[6]));
                currentOrder.setLaborCostPerSquareFoot(new BigDecimal(currentTokens[7]));
                currentOrder.setMaterialCost(new BigDecimal(currentTokens[8]));
                currentOrder.setLaborCost(new BigDecimal(currentTokens[9]));
                currentOrder.setTax(new BigDecimal(currentTokens[10]));
                currentOrder.setTotal(new BigDecimal(currentTokens[11]));
                // Put order into map
                orders.put(currentOrder.getOrderNumber(), currentOrder);
            }
            // Close scanner
            scanner.close();
        }
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        return orders.values()
                .stream()
                .filter(o -> o.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public int getNewOrderNumber() {
        if (orders.isEmpty()) {
            return 1;
        } else {
            int largestOrderNumber = orders.values()
                    .stream()
                    .mapToInt(o -> o.getOrderNumber())
                    .max()
                    .getAsInt();
            return largestOrderNumber + 1;
        }
    }

    @Override
    public Order addOrder(int orderNumber, Order order) {
        return orders.put(orderNumber, order);
    }

    @Override
    public Order getOrder(int orderNumber) {
        return orders.get(orderNumber);
    }

    @Override
    public Order editOrder(int orderNumber, Order order) {
        return orders.put(orderNumber, order);
    }

    @Override
    public Order removeOrder(int orderNumber) {
        return orders.remove(orderNumber);
    }

    @Override
    public void writeOrders() throws FlooringMasteryPersistenceException {
        // do nothing...
    }
    
    private List<String> getOrderFileList() throws FlooringMasteryPersistenceException {
        try {
            return Files.walk(Paths.get(ORDERS_PATH))
                    .filter(f -> f.toString().endsWith(".txt"))
                    .map(f -> f.toString())
                    .sorted()
                    .collect(Collectors.toList());
        } catch(IOException e) {
            throw new FlooringMasteryPersistenceException("Could not load order data into memory.", e);
        }
    }
    
}
