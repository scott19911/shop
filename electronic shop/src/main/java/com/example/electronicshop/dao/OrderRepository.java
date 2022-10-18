package com.example.electronicshop.dao;

import com.example.electronicshop.order.Order;
import com.example.electronicshop.order.OrderDetailsDTO;
import com.example.electronicshop.order.OrderInfo;
import com.example.electronicshop.products.Product;

import java.util.List;
import java.util.Map;

public interface OrderRepository {
    Map<Integer,String> getShippers();
    Map<Integer,String> getPayments();
    int insertOrder(Order order);
    int insertRecipient(OrderDetailsDTO orderDTO, int userId);
    int getAvailableQuantity(int productId);
    void updateAvailableQuantity(int productId,int quantity);
    boolean insertOrderDetails(Order order);
    List<OrderInfo> getUserOrders(int userId);
    Map<String,Map<Product,Integer>> getOrderProduct(int userId);
}
