package com.gateway.paymentdetails.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Data
public class OrderPaymentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderId;
    @Lob
    private String request;
    @Lob
    private  String response;
    @Lob
    private String webhookResponse;
}
