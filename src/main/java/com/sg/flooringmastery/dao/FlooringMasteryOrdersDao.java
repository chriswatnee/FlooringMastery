/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public interface FlooringMasteryOrdersDao {
    
    void loadOrders() throws FlooringMasteryPersistenceException;
    
    List<Order> getOrders(LocalDate date);
    
    int getNewOrderNumber();
    
    Order addOrder(int orderNumber, Order order);
    
    Order getOrder(int orderNumber);
    
    Order editOrder(int orderNumber, Order order);
    
    Order removeOrder(int orderNumber);
    
    void writeOrders() throws FlooringMasteryPersistenceException;
    
}
