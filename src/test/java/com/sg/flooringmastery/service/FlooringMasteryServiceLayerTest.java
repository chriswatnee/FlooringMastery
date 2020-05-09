/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryServiceLayerTest {
    
    private FlooringMasteryServiceLayer service;
    
    public FlooringMasteryServiceLayerTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringMasteryServiceLayer.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setMode method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testSetMode() throws Exception {
        service.setMode();
    }

    /**
     * Test of getOrders method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetOrdersWithOrders() throws Exception {
        LocalDate date = LocalDate.parse("2013-06-01");
        assertEquals(1, service.getOrders(date).size());
    }
    
    @Test
    public void testGetOrdersWithoutOrders() throws Exception {
        LocalDate date = LocalDate.parse("2019-06-25");

        try {
            service.getOrders(date);
            fail("Expected FlooringMasteryNoOrderException was not thrown.");
        } catch (FlooringMasteryNoOrderException e) {
            return;
        }
        
    }

    /**
     * Test of getNewOrderNumber method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetNewOrderNumber() {
        assertEquals(1, service.getNewOrderNumber());
    }

    /**
     * Test of validateOrderData method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testValidateOrderData() throws Exception {
        Order order = new Order(service.getNewOrderNumber());
        order.setDate(LocalDate.parse("2013-06-01"));
        order.setCustomerName("Wise");
        order.setState("OH");
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100.00"));
        service.validateOrderData(order);
    }
    
    @Test
    public void testValidateOrderDataInvalidState() throws Exception {
        Order order = new Order(service.getNewOrderNumber());
        order.setDate(LocalDate.parse("2013-06-01"));
        order.setCustomerName("Wise");
        order.setState("VA");
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100.00"));
        
        try {
            service.validateOrderData(order);
            fail("Expected FlooringMasteryDataValidationException was not thrown.");
        } catch (FlooringMasteryDataValidationException e) {
            return;
        }
        
    }
    
    @Test
    public void testValidateOrderDataInvalidProductType() throws Exception {
        Order order = new Order(service.getNewOrderNumber());
        order.setDate(LocalDate.parse("2013-06-01"));
        order.setCustomerName("Wise");
        order.setState("OH");
        order.setProductType("Marble");
        order.setArea(new BigDecimal("100.00"));
        
        try {
            service.validateOrderData(order);
            fail("Expected FlooringMasteryDataValidationException was not thrown.");
        } catch (FlooringMasteryDataValidationException e) {
            return;
        }
        
    }

    /**
     * Test of addOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testAddOrder() {
        Order order = new Order(service.getNewOrderNumber());
        order.setDate(LocalDate.parse("2013-06-01"));
        order.setCustomerName("Wise");
        order.setState("OH");
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100.00"));
        
        service.addOrder(order);
        
        Order expectedResult = new Order(1);
        expectedResult.setDate(LocalDate.parse("2013-06-01"));
        expectedResult.setCustomerName("Wise");
        expectedResult.setState("OH");
        expectedResult.setTaxRate(new BigDecimal("6.25"));
        expectedResult.setProductType("Wood");
        expectedResult.setArea(new BigDecimal("100.00"));
        expectedResult.setCostPerSquareFoot(new BigDecimal("5.15"));
        expectedResult.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        expectedResult.setMaterialCost(new BigDecimal("515.00"));
        expectedResult.setLaborCost(new BigDecimal("475.00"));
        expectedResult.setTax(new BigDecimal("61.88"));
        expectedResult.setTotal(new BigDecimal("1051.88"));
        
        assertEquals(expectedResult, order);
    }

    /**
     * Test of getOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetOrder() throws Exception {
        LocalDate date = LocalDate.parse("2013-06-01");
        int orderNumber = 1;
        Order order = service.getOrder(date, orderNumber);
        assertNotNull(order);
    }
    
    @Test
    public void testGetOrderNoDate() throws Exception {
        LocalDate date = LocalDate.parse("2019-06-25");
        int orderNumber = 1;
        
        try {
            service.getOrder(date, orderNumber);
            fail("Expected FlooringMasteryNoOrderException was not thrown.");
        } catch (FlooringMasteryNoOrderException e) {
            return;
        }
    }
    
    @Test
    public void testGetOrderNoOrderNumber() throws Exception {
        LocalDate date = LocalDate.parse("2013-06-01");
        int orderNumber = 2;
        
        try {
            service.getOrder(date, orderNumber);
            fail("Expected FlooringMasteryNoOrderException was not thrown.");
        } catch (FlooringMasteryNoOrderException e) {
            return;
        }
    }

    /**
     * Test of editOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testEditOrder() {
        Order order = new Order(1);
        order.setDate(LocalDate.parse("2019-06-25"));
        order.setCustomerName("Smart");
        order.setState("OH");
        order.setProductType("Wood");
        order.setArea(new BigDecimal("200.00"));
        
        service.editOrder(order);

        Order expectedResult = new Order(1);
        expectedResult.setDate(LocalDate.parse("2019-06-25"));
        expectedResult.setCustomerName("Smart");
        expectedResult.setState("OH");
        expectedResult.setTaxRate(new BigDecimal("6.25"));
        expectedResult.setProductType("Wood");
        expectedResult.setArea(new BigDecimal("200.00"));
        expectedResult.setCostPerSquareFoot(new BigDecimal("5.15"));
        expectedResult.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        expectedResult.setMaterialCost(new BigDecimal("1030.00"));
        expectedResult.setLaborCost(new BigDecimal("950.00"));
        expectedResult.setTax(new BigDecimal("123.75"));
        expectedResult.setTotal(new BigDecimal("2103.75"));
        
        assertEquals(expectedResult, order);
    }

    /**
     * Test of removeOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testRemoveOrder() {
        Order order = service.removeOrder(1);
        assertNotNull(order);
        order = service.removeOrder(2);
        assertNull(order);
    }
    
}
