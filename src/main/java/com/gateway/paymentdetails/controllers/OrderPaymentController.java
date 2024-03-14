package com.gateway.paymentdetails.controllers;

import com.gateway.paymentdetails.Dtos.*;
import com.gateway.paymentdetails.models.OrderPaymentStatus;
import com.gateway.paymentdetails.services.OrderPaymentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/api")
public class OrderPaymentController {
    @Autowired
    private OrderPaymentService orderPaymentService;

    @PostMapping("/order")
    public ResponseEntity<OrderPaymentStatus> createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto, @RequestHeader Map<String, String> requestHeaders) {
        return new ResponseEntity<>(orderPaymentService.createOrderService(createOrderRequestDto, requestHeaders), HttpStatus.CREATED);
    }

    @GetMapping(value = {"/order/", "/order/{id}"})
    public ResponseEntity<?> getOrders(@PathVariable(required = false) Long id, @RequestHeader Map<String, String> requestHeaders) {
        if (id != null) {
            return ResponseEntity.ok(orderPaymentService.getOrderDetailsById(id));
        } else {
            return ResponseEntity.ok(orderPaymentService.getOrderDetails());
        }

    }

    @PostMapping("/webhook")
    public ResponseEntity<String> callWebhook(@RequestBody String requestBody) {

        return ResponseEntity.ok(orderPaymentService.updateWebhook(requestBody));
    }
}
