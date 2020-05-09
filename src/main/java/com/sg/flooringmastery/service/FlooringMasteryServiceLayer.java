/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public interface FlooringMasteryServiceLayer {
    
    void loadConfig() throws FlooringMasteryPersistenceException;
    
    void setMode() throws FlooringMasteryDataValidationException;
    
    void loadData() throws FlooringMasteryPersistenceException;
    
    List<Order> getOrders(LocalDate date) throws FlooringMasteryNoOrderException;
    
    int getNewOrderNumber();
    
    void validateOrderData(Order order) throws FlooringMasteryDataValidationException;
    
    void addOrder(Order order);
    
    Order getOrder(LocalDate date, int orderNumber) throws FlooringMasteryNoOrderException;
    
    Order editOrder(Order order);
    
    Order removeOrder(int orderNumber);
    
    void writeOrders() throws FlooringMasteryPersistenceException;
    
}
