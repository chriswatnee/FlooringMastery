/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryOrdersDaoTest {
    
    private FlooringMasteryOrdersDao ordersDao;
    
    public FlooringMasteryOrdersDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        ordersDao = ctx.getBean("ordersDaoTraining", FlooringMasteryOrdersDao.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Order> orderList1 = ordersDao.getOrders(LocalDate.parse("2013-06-01"));
        for (Order order : orderList1) {
            ordersDao.removeOrder(order.getOrderNumber());
        }
        
        List<Order> orderList2 = ordersDao.getOrders(LocalDate.parse("2019-06-25"));
        for (Order order : orderList2) {
            ordersDao.removeOrder(order.getOrderNumber());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getOrders method, of class FlooringMasteryOrdersDao.
     */
    @Test
    public void testGetOrders() {
    }

    /**
     * Test of getNewOrderNumber method, of class FlooringMasteryOrdersDao.
     */
    @Test
    public void testGetNewOrderNumber() {
        assertEquals(1, ordersDao.getNewOrderNumber());
        
        Order order1 = new Order(1);
        order1.setDate(LocalDate.parse("2013-06-01"));
        order1.setCustomerName("Wise");
        order1.setState("OH");
        order1.setTaxRate(new BigDecimal("6.25"));
        order1.setProductType("Wood");
        order1.setArea(new BigDecimal("100.00"));
        order1.setCostPerSquareFoot(new BigDecimal("5.15"));
        order1.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order1.setMaterialCost(new BigDecimal("515.00"));
        order1.setLaborCost(new BigDecimal("475.00"));
        order1.setTax(new BigDecimal("61.88"));
        order1.setTotal(new BigDecimal("1051.88"));
        
        ordersDao.addOrder(order1.getOrderNumber(), order1);
        
        assertEquals(2, ordersDao.getNewOrderNumber());
        
        Order order2 = new Order(2);
        order2.setDate(LocalDate.parse("2013-06-01"));
        order2.setCustomerName("Wise");
        order2.setState("OH");
        order2.setTaxRate(new BigDecimal("6.25"));
        order2.setProductType("Wood");
        order2.setArea(new BigDecimal("100.00"));
        order2.setCostPerSquareFoot(new BigDecimal("5.15"));
        order2.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order2.setMaterialCost(new BigDecimal("515.00"));
        order2.setLaborCost(new BigDecimal("475.00"));
        order2.setTax(new BigDecimal("61.88"));
        order2.setTotal(new BigDecimal("1051.88"));
        
        ordersDao.addOrder(order2.getOrderNumber(), order2);
        
        assertEquals(3, ordersDao.getNewOrderNumber());
        
        ordersDao.removeOrder(1);
        
        assertEquals(3, ordersDao.getNewOrderNumber());
    }

    /**
     * Test of addOrder method, of class FlooringMasteryOrdersDao.
     */
    @Test
    public void testAddGetOrder() {
        Order order = new Order(1);
        order.setDate(LocalDate.parse("2013-06-01"));
        order.setCustomerName("Wise");
        order.setState("OH");
        order.setTaxRate(new BigDecimal("6.25"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100.00"));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(new BigDecimal("515.00"));
        order.setLaborCost(new BigDecimal("475.00"));
        order.setTax(new BigDecimal("61.88"));
        order.setTotal(new BigDecimal("1051.88"));
        
        ordersDao.addOrder(order.getOrderNumber(), order);
        
        Order fromOrdersDao = ordersDao.getOrder(order.getOrderNumber());
        
        assertEquals(order, fromOrdersDao);
    }

    /**
     * Test of editOrder method, of class FlooringMasteryOrdersDao.
     */
    @Test
    public void testEditOrder() {
        Order order = new Order(1);
        order.setDate(LocalDate.parse("2013-06-01"));
        order.setCustomerName("Wise");
        order.setState("OH");
        order.setTaxRate(new BigDecimal("6.25"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100.00"));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(new BigDecimal("515.00"));
        order.setLaborCost(new BigDecimal("475.00"));
        order.setTax(new BigDecimal("61.88"));
        order.setTotal(new BigDecimal("1051.88"));
        
        ordersDao.editOrder(order.getOrderNumber(), order);
        
        Order fromOrdersDao = ordersDao.getOrder(order.getOrderNumber());
        
        assertEquals(order, fromOrdersDao);
    }

    /**
     * Test of removeOrder method, of class FlooringMasteryOrdersDao.
     */
    @Test
    public void testRemoveOrder() {
        Order order1 = new Order(1);
        order1.setDate(LocalDate.parse("2013-06-01"));
        order1.setCustomerName("Wise");
        order1.setState("OH");
        order1.setTaxRate(new BigDecimal("6.25"));
        order1.setProductType("Wood");
        order1.setArea(new BigDecimal("100.00"));
        order1.setCostPerSquareFoot(new BigDecimal("5.15"));
        order1.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order1.setMaterialCost(new BigDecimal("515.00"));
        order1.setLaborCost(new BigDecimal("475.00"));
        order1.setTax(new BigDecimal("61.88"));
        order1.setTotal(new BigDecimal("1051.88"));
        
        ordersDao.addOrder(order1.getOrderNumber(), order1);
        
        Order order2 = new Order(2);
        order2.setDate(LocalDate.parse("2013-06-01"));
        order2.setCustomerName("Wise");
        order2.setState("OH");
        order2.setTaxRate(new BigDecimal("6.25"));
        order2.setProductType("Wood");
        order2.setArea(new BigDecimal("100.00"));
        order2.setCostPerSquareFoot(new BigDecimal("5.15"));
        order2.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order2.setMaterialCost(new BigDecimal("515.00"));
        order2.setLaborCost(new BigDecimal("475.00"));
        order2.setTax(new BigDecimal("61.88"));
        order2.setTotal(new BigDecimal("1051.88"));
        
        ordersDao.addOrder(order2.getOrderNumber(), order2);
        
        ordersDao.removeOrder(order1.getOrderNumber());
        assertEquals(1, ordersDao.getOrders(LocalDate.parse("2013-06-01")).size());
        assertNull(ordersDao.getOrder(order1.getOrderNumber()));
        
        ordersDao.removeOrder(order2.getOrderNumber());
        assertEquals(0, ordersDao.getOrders(LocalDate.parse("2013-06-01")).size());
        assertNull(ordersDao.getOrder(order2.getOrderNumber()));
    }
    
}
