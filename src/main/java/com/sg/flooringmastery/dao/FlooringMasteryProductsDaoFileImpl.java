/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryProductsDaoFileImpl implements FlooringMasteryProductsDao {
    
    public static final String PRODUCTS_FILE = "Products.txt";
    public static final String DELIMITER = ",";
    private Map<String, Product> products = new HashMap<>();

    @Override
    public void loadProducts() throws FlooringMasteryPersistenceException {
         Scanner scanner;
        
        try {
            // Create Scanner for reading the products file
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load products data into memory.", e);
        }
        
        String currentLine;
        String[] currentTokens;
        
        // Skip first line
        scanner.nextLine();
        
        // Loop through all the lines in the products file
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            // Break line into tokens
            currentTokens = currentLine.split(DELIMITER);
            // Create product based on tokens
            Product currentProduct = new Product(currentTokens[0]);
            BigDecimal costPerSquareFoot = new BigDecimal(currentTokens[1]).setScale(2, RoundingMode.HALF_UP);
            BigDecimal laborCostPerSquareFoot = new BigDecimal(currentTokens[2]).setScale(2, RoundingMode.HALF_UP);
            currentProduct.setCostPerSquareFoot(costPerSquareFoot);
            currentProduct.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
            // Put product into map
            products.put(currentProduct.getProductType(), currentProduct);
        }
        // Close scanner
        scanner.close();
    }
    
    @Override
    public Product addProduct(String productType, Product product) {
        return products.put(productType, product);
    }

    @Override
    public Product getProduct(String productType) {
        return products.get(productType);
    }
    
}
