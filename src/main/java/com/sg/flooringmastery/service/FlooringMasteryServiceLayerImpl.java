/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryConfigDao;
import com.sg.flooringmastery.dao.FlooringMasteryOrdersDao;
import com.sg.flooringmastery.dao.FlooringMasteryOrdersDaoProdImpl;
import com.sg.flooringmastery.dao.FlooringMasteryOrdersDaoTrainingImpl;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dao.FlooringMasteryProductsDao;
import com.sg.flooringmastery.dao.FlooringMasteryTaxesDao;
import com.sg.flooringmastery.dto.ConfigSetting;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {
    
    private FlooringMasteryConfigDao configDao;
    private FlooringMasteryOrdersDao ordersDao;
    private FlooringMasteryTaxesDao taxesDao;
    private FlooringMasteryProductsDao productsDao;
    
    public FlooringMasteryServiceLayerImpl(FlooringMasteryConfigDao configDao, FlooringMasteryOrdersDao ordersDao, FlooringMasteryTaxesDao taxesDao, FlooringMasteryProductsDao productsDao) {
        this.configDao = configDao;
        this.ordersDao = ordersDao;
        this.taxesDao = taxesDao;
        this.productsDao = productsDao;
    }

    @Override
    public void loadConfig() throws FlooringMasteryPersistenceException {
        configDao.loadConfig();
    }
    
    @Override
    public void setMode() throws FlooringMasteryDataValidationException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        ConfigSetting modeConfigSetting = configDao.getConfigSetting("mode");
        
        // Test if valid state was entered
        if (modeConfigSetting == null) {
            throw new FlooringMasteryDataValidationException("ERROR: No mode set in configuration file.");
        }
        
        String mode = modeConfigSetting.getValue();
        
        if (mode.equalsIgnoreCase("Prod")) {
            ordersDao = ctx.getBean("ordersDaoProd", FlooringMasteryOrdersDao.class);
        } else if (mode.equalsIgnoreCase("Training")) {
            ordersDao = ctx.getBean("ordersDaoTraining", FlooringMasteryOrdersDao.class);
        } else {
            throw new FlooringMasteryDataValidationException("ERROR: Mode: " + mode + " is invalid.");
        }
    }
    
    @Override
    public void loadData() throws FlooringMasteryPersistenceException {
        ordersDao.loadOrders();
        taxesDao.loadTaxes();
        productsDao.loadProducts();
    }

    @Override
    public List<Order> getOrders(LocalDate date) throws FlooringMasteryNoOrderException {
        
        // Check to see if there are any orders for the given date
        // If not, throw a FlooringMasteryNoOrderException
        if (ordersDao.getOrders(date).isEmpty()) {
            String dateString = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            throw new FlooringMasteryNoOrderException("ERROR: No order(s) for " + dateString);
        }
        
        return ordersDao.getOrders(date);
    }

    @Override
    public int getNewOrderNumber() {
        return ordersDao.getNewOrderNumber();
    }
    
    @Override
    public void validateOrderData(Order order) throws FlooringMasteryDataValidationException {
        Tax tax = taxesDao.getTax(order.getState());
        Product product = productsDao.getProduct(order.getProductType());
        
        // Test if valid state was entered
        if (tax == null) {
            throw new FlooringMasteryDataValidationException("ERROR: Not a valid state.");
        }
        
        // Test if valid product type was entered
        if (product == null) {
            throw new FlooringMasteryDataValidationException("ERROR: Not a valid product type.");
        }
    }

    @Override
    public void addOrder(Order order) {
        order = completeOrderData(order);
        ordersDao.addOrder(order.getOrderNumber(), order);
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws FlooringMasteryNoOrderException {
        Order order = ordersDao.getOrder(orderNumber);
        
        // Check to see if there is an order with the given date and order number
        // If not, throw a FlooringMasteryNoOrderException
        if (order == null || !order.getDate().equals(date)) {
            throw new FlooringMasteryNoOrderException("ERROR: No order with the provided date and order number.");
        }
        
        return order;
    }

    @Override
    public Order editOrder(Order order) {
        order = completeOrderData(order);
        return ordersDao.editOrder(order.getOrderNumber(), order);
    }

    @Override
    public Order removeOrder(int orderNumber) {
        return ordersDao.removeOrder(orderNumber);
    }
        
    @Override
    public void writeOrders() throws FlooringMasteryPersistenceException {
        ordersDao.writeOrders();
    }
    
    private Order completeOrderData(Order order) {
        Tax tax = taxesDao.getTax(order.getState());
        Product product = productsDao.getProduct(order.getProductType());
        // Set properties based on state and product type
        BigDecimal taxRate = tax.getTaxRate();
        BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
        order.setTaxRate(taxRate);
        order.setCostPerSquareFoot(costPerSquareFoot);
        order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        // Calculate and set remaining properties
        BigDecimal area = order.getArea();
        BigDecimal materialCost = area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        BigDecimal taxAsPercent = taxRate.divide(new BigDecimal("100"));
        BigDecimal totalWithOutTax = materialCost.add(laborCost);
        BigDecimal taxTotal = totalWithOutTax.multiply(taxAsPercent).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = totalWithOutTax.add(taxTotal).setScale(2, RoundingMode.HALF_UP);
        order.setTax(taxTotal);
        order.setTotal(total);
        
        return order;
    }
    
}
