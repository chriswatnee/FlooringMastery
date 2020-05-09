/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryTaxesDaoFileImpl implements FlooringMasteryTaxesDao {
    
    public static final String TAXES_FILE = "Taxes.txt";
    public static final String DELIMITER = ",";
    private Map<String, Tax> taxes = new HashMap<>();

    @Override
    public void loadTaxes() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        
        try {
            // Create Scanner for reading the taxes file
            scanner = new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load taxes data into memory.", e);
        }
        
        String currentLine;
        String[] currentTokens;
        
        // Skip first line
        scanner.nextLine();
        
        // Loop through all the lines in the taxes file
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            // Break line into tokens
            currentTokens = currentLine.split(DELIMITER);
            // Create tax based on tokens
            Tax currentTax = new Tax(currentTokens[0]);
            BigDecimal taxRate = new BigDecimal(currentTokens[1]).setScale(2, RoundingMode.HALF_UP);
            currentTax.setTaxRate(taxRate);
            // Put tax into map
            taxes.put(currentTax.getState(), currentTax);
        }
        // Close scanner
        scanner.close();
    }
    
    @Override
    public Tax addTax(String state, Tax tax) {
        return taxes.put(state, tax);
    }

    @Override
    public Tax getTax(String state) {
        return taxes.get(state);
    }
    
}
