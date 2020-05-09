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
public class FlooringMasteryConfigDaoStubImpl implements FlooringMasteryConfigDao {
    
    ConfigSetting onlyConfigSetting;
    
    public FlooringMasteryConfigDaoStubImpl() {
        onlyConfigSetting = new ConfigSetting("mode");
        onlyConfigSetting.setValue("prod");
    }

    @Override
    public void loadConfig() throws FlooringMasteryPersistenceException {
        // do nothing...
    }

    @Override
    public ConfigSetting addConfigSetting(String property, ConfigSetting configSetting) {
        if (property.equals(onlyConfigSetting.getProperty())) {
            return onlyConfigSetting;
        } else {
            return null;
        }
    }
    
    @Override
    public ConfigSetting getConfigSetting(String property) {
        if (property.equals(onlyConfigSetting.getProperty())) {
            return onlyConfigSetting;
        } else {
            return null;
        }
    }
    
}
