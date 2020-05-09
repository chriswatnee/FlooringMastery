/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryProductsDaoStubImpl implements FlooringMasteryProductsDao {
    
    Product onlyProduct;
    
    public FlooringMasteryProductsDaoStubImpl() {
        onlyProduct = new Product("Wood");
        BigDecimal costPerSquareFoot = new BigDecimal("5.15").setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCostPerSquareFoot = new BigDecimal("4.75").setScale(2, RoundingMode.HALF_UP);
        onlyProduct.setCostPerSquareFoot(costPerSquareFoot);
        onlyProduct.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
    }

    @Override
    public void loadProducts() throws FlooringMasteryPersistenceException {
        // do nothing...
    }
    
    @Override
    public Product addProduct(String productType, Product product) {
        if (productType.equals(onlyProduct.getProductType())) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public Product getProduct(String productType) {
        if (productType.equals(onlyProduct.getProductType())) {
            return onlyProduct;
        } else {
            return null;
        }
    }
    
}
