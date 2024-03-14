package com.gateway.paymentdetails.services;

import com.gateway.paymentdetails.Dtos.CreateOrderRequestDto;
import com.gateway.paymentdetails.Dtos.CreateOrderResponseDto;
import com.gateway.paymentdetails.Exceptions.OrderNotFoundException;
import com.gateway.paymentdetails.models.OrderPaymentStatus;
import com.gateway.paymentdetails.repositories.OrderPaymentRepository;
import com.google.gson.JsonArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderPaymentServicesImpl implements OrderPaymentService {
    @Autowired
    private OrderPaymentRepository orderPaymentRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("{$.order.url}")
    public String orderUrl;

    @Override
    public OrderPaymentStatus createOrderService(CreateOrderRequestDto createOrderRequestDto, Map<String, String> requestHeaders) throws RuntimeException {
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("NX-Token", requestHeaders.get("token"));
        OrderPaymentStatus orderPaymentStatus = new OrderPaymentStatus();
        HttpEntity<CreateOrderRequestDto> httpOrderEntity = new HttpEntity<>(createOrderRequestDto, headers);
        ResponseEntity<CreateOrderResponseDto> responseEntity = restTemplate.exchange(orderUrl+"order/plan", HttpMethod.POST, httpOrderEntity, CreateOrderResponseDto.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            CreateOrderResponseDto createOrderResponseDto = responseEntity.getBody();
            if (createOrderResponseDto != null) {
                orderPaymentStatus.setOrderId(createOrderResponseDto.id());
                orderPaymentStatus.setRequest(createOrderRequestDto.toString());
                orderPaymentStatus.setResponse(createOrderResponseDto.toString());
            }
        } else {
            throw new RuntimeException("unable insert the order record ");// Failure Response Code
        }

        return orderPaymentStatus;
    }

    @Override
    public OrderPaymentStatus getOrderDetailsById(Long id) throws RuntimeException {
        Optional<OrderPaymentStatus> optionalOrderPaymentStatus = orderPaymentRepository.findById(id);
        if (optionalOrderPaymentStatus.isPresent()) {
            return optionalOrderPaymentStatus.get();
        } else {
            throw new OrderNotFoundException("order not found with the given id");
        }

    }

    @Override
    public List<OrderPaymentStatus> getOrderDetails() {
        Optional<List<OrderPaymentStatus>> optionalOrderPaymentStatus = Optional.of(orderPaymentRepository.findAll());
        return optionalOrderPaymentStatus.get();
    }

    @Override
    public String updateWebhook(String requestBody) {
        JSONObject jsonObject = new JSONObject(requestBody);
        String order_id = jsonObject.getJSONObject("order").optString("id");
        OrderPaymentStatus orderPaymentStatus;
        JsonArray jsonArray = new JsonArray();
        Optional<OrderPaymentStatus> optionalOrderPaymentStatus = Optional.ofNullable(orderPaymentRepository.findByOrderId(order_id));
        if (optionalOrderPaymentStatus.isPresent()) {
            orderPaymentStatus = optionalOrderPaymentStatus.get();
            if (orderPaymentStatus.getWebhookResponse() != null) {
                jsonArray.add(orderPaymentStatus.getWebhookResponse());
            }
            jsonArray.add(requestBody);
            orderPaymentStatus.setWebhookResponse(jsonArray.toString());
            return orderPaymentStatus.toString();
        } else {
            return "OrderId not found";
        }
    }

}
