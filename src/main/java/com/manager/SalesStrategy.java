package com.manager;/*
 * Strategy Pattern
 * Will decide between two algorithms for tax purposes on price
 */

public interface SalesStrategy {
    double setTaxRate(Double basePrice);
}
