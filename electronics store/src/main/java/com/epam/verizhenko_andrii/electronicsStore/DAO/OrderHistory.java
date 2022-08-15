package com.epam.verizhenko_andrii.electronicsStore.DAO;

import com.epam.verizhenko_andrii.electronicsStore.products.Product;

import java.util.LinkedHashMap;

public interface OrderHistory {
     LinkedHashMap<Integer, Product> getHistory();
     void addToHistory(int key, Product product);
}
