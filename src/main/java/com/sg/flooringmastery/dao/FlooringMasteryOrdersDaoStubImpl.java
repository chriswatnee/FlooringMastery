/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryOrdersDaoStubImpl implements FlooringMasteryOrdersDao {
    
    Order onlyOrder;
    List<Order> orderList = new ArrayList<>();
    
    public FlooringMasteryOrdersDaoStubImpl() {
        onlyOrder = new Order(1);
        onlyOrder.setDate(LocalDate.parse("2013-06-01"));
        onlyOrder.setCustomerName("Wise");
        onlyOrder.setState("OH");
        onlyOrder.setTaxRate(new BigDecimal("6.25"));
        onlyOrder.setProductType("Wood");
        onlyOrder.setArea(new BigDecimal("100.00"));
        onlyOrder.setCostPerSquareFoot(new BigDecimal("5.15"));
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        onlyOrder.setMaterialCost(new BigDecimal("515.00"));
        onlyOrder.setLaborCost(new BigDecimal("475.00"));
        onlyOrder.setTax(new BigDecimal("61.88"));
        onlyOrder.setTotal(new BigDecimal("1051.88"));
        
        orderList.add(onlyOrder);
    }
    
    @Override
    public void loadOrders() {
        // do nothing...
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        if (date.equals(onlyOrder.getDate())) {
            return orderList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public int getNewOrderNumber() {
        return onlyOrder.getOrderNumber();
    }

    @Override
    public Order addOrder(int orderNumber, Order order) {
        if (orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order getOrder(int orderNumber) {
        if (orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(int orderNumber, Order order) {
        if (orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(int orderNumber) {
        if (orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public void writeOrders() {
        // do nothing...
    }
    
}
