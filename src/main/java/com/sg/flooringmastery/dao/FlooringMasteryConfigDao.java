/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.ConfigSetting;

/**
 *
 * @author christopherwatnee
 */
public interface FlooringMasteryConfigDao {
    
    void loadConfig() throws FlooringMasteryPersistenceException;
    
    ConfigSetting addConfigSetting(String property, ConfigSetting configSetting);
    
    ConfigSetting getConfigSetting(String property);
    
}
