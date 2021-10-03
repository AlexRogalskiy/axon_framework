package com.github.querymodel;

import com.github.coreapi.events.OrderConfirmedEvent;
import com.github.coreapi.events.OrderCreatedEvent;
import com.github.coreapi.events.OrderShippedEvent;
import com.github.coreapi.queries.FindAllOrderedProductsQuery;
import com.github.coreapi.queries.Order;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Service
//@ProcessingGroup("orders")
public class OrdersEventHandler {

    private final Map<String, Order> orders = new HashMap<>();

    @EventHandler
    public void on(OrderCreatedEvent event) {
        String orderId = event.getOrderId();
        orders.put(orderId, new Order(orderId));
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        orders.computeIfPresent(event.getOrderId(), (orderId, orders) -> {
            orders.setOrderConfirmed();
            return orders;
        });
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        orders.computeIfPresent(event.getOrderId(), (orderId, orders) -> {
            orders.setOrderShipped();
            return orders;
        });
    }

    @QueryHandler
    public List<Order> handle(FindAllOrderedProductsQuery query) {
        return new ArrayList<>(orders.values());
    }
}
