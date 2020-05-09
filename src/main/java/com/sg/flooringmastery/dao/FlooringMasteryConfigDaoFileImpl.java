/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.ConfigSetting;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryConfigDaoFileImpl implements FlooringMasteryConfigDao {
    
    public static final String CONFIG_FILE = "Configuration.txt";
    public static final String DELIMITER = "::";
    private Map<String, ConfigSetting> configSettings = new HashMap<>();

    @Override
    public void loadConfig() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        
        try {
            // Create Scanner for reading the config file
            scanner = new Scanner(new BufferedReader(new FileReader(CONFIG_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load configuration data into memory.", e);
        }
        
        String currentLine;
        String[] currentTokens;
        
        // Loop through all the lines in the config file
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            // Break line into tokens
            currentTokens = currentLine.split(DELIMITER);
            // Create config setting based on tokens
            ConfigSetting currentConfigSetting = new ConfigSetting(currentTokens[0]);
            currentConfigSetting.setValue(currentTokens[1]);
            // Put config setting into map
            configSettings.put(currentConfigSetting.getProperty(), currentConfigSetting);
        }
        // Close scanner
        scanner.close();
    }
    
    @Override
    public ConfigSetting addConfigSetting(String property, ConfigSetting configSetting) {
        return configSettings.put(property, configSetting);
    }
    
    @Override
    public ConfigSetting getConfigSetting(String property) {
        return configSettings.get(property);
    }
    
}
