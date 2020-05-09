/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import static com.sg.flooringmastery.dao.FlooringMasteryOrdersDaoTrainingImpl.ORDERS_PATH;
import com.sg.flooringmastery.dto.Order;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryOrdersDaoProdImpl extends FlooringMasteryOrdersDaoTrainingImpl implements FlooringMasteryOrdersDao {
    
    public static final String ORDERS_PREFIX = "Orders_";
    public static final String ORDERS_EXTENSION = ".txt";
    
    @Override
    public void writeOrders() throws FlooringMasteryPersistenceException {
        // Delete existing files
        deleteOrderFiles();
        
        // Create a map grouped by date
        Map<String, List<Order>> orderMap = orders.values()
                .stream()
                .collect(Collectors.groupingBy(o -> o.getDate().format(DateTimeFormatter.ofPattern("MMddyyyy"))));
        
        Set<String> keys = orderMap.keySet();
        
        // Loop through all dates
        for (String key : keys) {
            PrintWriter out;
            String ordersFileName = ORDERS_PREFIX + key + ORDERS_EXTENSION;

            try {
                 // Create PrintWriter for writing to the order file
                out = new PrintWriter(new FileWriter(ORDERS_PATH + "/" + ordersFileName));
            } catch (IOException e) {
                throw new FlooringMasteryPersistenceException("Could not save order data.");
            }
            List<Order> orderList = orderMap.get(key);
            // Loop through all orders
            for (Order currentOrder : orderList) {
                // Write order to the file
                out.println(currentOrder.getOrderNumber() + DELIMITER
                        + currentOrder.getCustomerName() + DELIMITER
                        + currentOrder.getState() + DELIMITER
                        + currentOrder.getTaxRate() + DELIMITER
                        + currentOrder.getProductType() + DELIMITER
                        + currentOrder.getArea() + DELIMITER
                        + currentOrder.getCostPerSquareFoot() + DELIMITER
                        + currentOrder.getLaborCostPerSquareFoot() + DELIMITER
                        + currentOrder.getMaterialCost() + DELIMITER
                        + currentOrder.getLaborCost() + DELIMITER
                        + currentOrder.getTax() + DELIMITER
                        + currentOrder.getTotal());
                // Force line to be writen to file
                out.flush();
            }
            // Close stream
            out.close();
        }
    }
    
    private void deleteOrderFiles() throws FlooringMasteryPersistenceException {
        try {
            Files.walk(Paths.get(ORDERS_PATH))
                    .filter(f -> f.toString().endsWith(".txt"))
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch(IOException e) {
            throw new FlooringMasteryPersistenceException("Could not delete order data into memory.", e);
        }
    }
}
