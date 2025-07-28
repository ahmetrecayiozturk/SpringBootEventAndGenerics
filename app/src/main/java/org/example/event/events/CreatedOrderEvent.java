package org.example.event.events;

import org.example.event.event_generic.AppEvent;
import org.example.model.Order;

// Order oluşması eventini oluşturalum, bu event oluştuğunda birazdan yazacağım service'de bu eventi dinleyip ona göre bir işlem yapacağız
public class CreatedOrderEvent implements AppEvent<Order> {
    private Order order;
    public CreatedOrderEvent(Order order) {
        this.order = order;
    }
    @Override
    public Order getSource() {
        return order;
    }
}
