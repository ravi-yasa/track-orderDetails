package com.gateway.paymentdetails.repositories;

import com.gateway.paymentdetails.models.OrderPaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPaymentStatus,Long> {
   OrderPaymentStatus findByOrderId(String orderId);
}
