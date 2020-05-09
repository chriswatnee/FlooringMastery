/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
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
public class FlooringMasteryTaxesDaoTest {
    
    private FlooringMasteryTaxesDao taxesDao;
    
    public FlooringMasteryTaxesDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        taxesDao = ctx.getBean("taxesDao", FlooringMasteryTaxesDao.class);
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
     * Test of addTax method, of class FlooringMasteryTaxesDao.
     */
    @Test
    public void testAddGetTax() {
        Tax tax = new Tax("OH");
        BigDecimal taxRate = new BigDecimal("6.25").setScale(2, RoundingMode.HALF_UP);
        tax.setTaxRate(taxRate);

        taxesDao.addTax(tax.getState(), tax);
        
        Tax fromTaxesDao = taxesDao.getTax(tax.getState());
        
        assertEquals(tax, fromTaxesDao);
    }
    
}
