/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryProductsDaoTest {
    
    private FlooringMasteryProductsDao productsDao;
    
    public FlooringMasteryProductsDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        productsDao = ctx.getBean("productsDao", FlooringMasteryProductsDao.class);
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
     * Test of addProduct method, of class FlooringMasteryProductsDao.
     */
    @Test
    public void testAddGetProduct() {
        Product product = new Product("Wood");
        BigDecimal costPerSquareFoot = new BigDecimal("5.15").setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCostPerSquareFoot = new BigDecimal("4.75").setScale(2, RoundingMode.HALF_UP);
        product.setCostPerSquareFoot(costPerSquareFoot);
        product.setLaborCostPerSquareFoot(laborCostPerSquareFoot);

        productsDao.addProduct(product.getProductType(), product);
        
        Product fromProductDao = productsDao.getProduct(product.getProductType());
        
        assertEquals(product, fromProductDao);
    }
    
}
