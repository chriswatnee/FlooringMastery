/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.ConfigSetting;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryConfigDaoTest {
    
    private FlooringMasteryConfigDao configDao;
    
    public FlooringMasteryConfigDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        configDao = ctx.getBean("configDao", FlooringMasteryConfigDao.class);
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
     * Test of addConfigSetting method, of class FlooringMasteryConfigDao.
     */
    @Test
    public void testAddGetConfigSetting() {
        ConfigSetting configSetting = new ConfigSetting("mode");
        configSetting.setValue("prod");

        configDao.addConfigSetting(configSetting.getProperty(), configSetting);
        
        ConfigSetting fromConfigDao = configDao.getConfigSetting(configSetting.getProperty());
        
        assertEquals(configSetting, fromConfigDao);
    }
    
}
