package com.gateway.paymentdetails.services;

import com.gateway.paymentdetails.Dtos.CreateOrderRequestDto;
import com.gateway.paymentdetails.models.OrderPaymentStatus;

import java.util.List;
import java.util.Map;

public interface OrderPaymentService {
    OrderPaymentStatus createOrderService(CreateOrderRequestDto createOrderRequestDto, Map<String, String> requestHeaders);
    OrderPaymentStatus getOrderDetailsById(Long id);
    List<OrderPaymentStatus> getOrderDetails();
    String updateWebhook(String requestBody);


}
