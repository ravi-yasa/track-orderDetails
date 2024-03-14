package com.gateway.paymentdetails.models;

import java.util.Date;

public record Invoice(String desc, Long invoiceNo, String debitor, String name, Double amount, Date dueDate , Date invoice_date) {
}
