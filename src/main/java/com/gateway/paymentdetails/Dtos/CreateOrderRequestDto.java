package com.gateway.paymentdetails.Dtos;


import com.gateway.paymentdetails.models.OrderEntity;

public record CreateOrderRequestDto(String orgId, OrderEntity order) {
}
