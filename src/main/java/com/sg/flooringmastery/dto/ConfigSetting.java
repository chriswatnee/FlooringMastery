/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.util.Objects;

/**
 *
 * @author christopherwatnee
 */
public class ConfigSetting {
    
    private String property;
    private String value;
    
    public ConfigSetting(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.property);
        hash = 11 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfigSetting other = (ConfigSetting) obj;
        if (!Objects.equals(this.property, other.property)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
    
}
