package com.gateway.paymentdetails.models;


import java.util.List;

public record OrderEntity(String name, List<Asset> assets, List<Invoice> invoice) {
}
