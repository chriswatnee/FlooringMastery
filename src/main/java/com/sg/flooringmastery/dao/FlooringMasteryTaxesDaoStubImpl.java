/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryTaxesDaoStubImpl implements FlooringMasteryTaxesDao {
    
    Tax onlyTax;
    
    public FlooringMasteryTaxesDaoStubImpl() {
        onlyTax = new Tax("OH");
        BigDecimal taxRate = new BigDecimal("6.25").setScale(2, RoundingMode.HALF_UP);
        onlyTax.setTaxRate(taxRate);
    }

    @Override
    public void loadTaxes() throws FlooringMasteryPersistenceException {
        // do nothing...
    }
    
    @Override
    public Tax addTax(String state, Tax tax) {
        if (state.equals(onlyTax.getState())) {
            return onlyTax;
        } else {
            return null;
        }
    }

    @Override
    public Tax getTax(String state) {
        if (state.equals(onlyTax.getState())) {
            return onlyTax;
        } else {
            return null;
        }
    }
    
}
