package org.example.controller;

import org.aspectj.weaver.ast.Or;
import org.example.event.event_service.DomainEventPublisher;
import org.example.event.events.CreatedOrderEvent;
import org.example.event.events.UpdatedOrderEvent;
import org.example.generics.ApiResponse;
import org.example.model.Order;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    DomainEventPublisher publisher;

    public OrderController(DomainEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RequestMapping("/create")
    public ApiResponse<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = new Order();
        createdOrder.setId(order.getId());
        createdOrder.setProductName(order.getProductName());
        createdOrder.setQuantity(order.getQuantity());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Success");
        apiResponse.setSuccess(true);
        apiResponse.setData(createdOrder);
        CreatedOrderEvent createdOrderEvent = new CreatedOrderEvent(order);
        publisher.publish(createdOrderEvent);
        return apiResponse;
    }
    @RequestMapping("/update")
    public ApiResponse<Order> updateOrder(@RequestBody Order order) {
        Order updatedOrder = new Order();
        updatedOrder.setId(order.getId());
        updatedOrder.setProductName(order.getProductName());
        updatedOrder.setQuantity(order.getQuantity());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Success");
        apiResponse.setSuccess(true);
        apiResponse.setData(updatedOrder);
        UpdatedOrderEvent updatedOrderEvent = new UpdatedOrderEvent(order);
        publisher.publish(updatedOrderEvent);
        return apiResponse;
    }
}
