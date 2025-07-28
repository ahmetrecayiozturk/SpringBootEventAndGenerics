package org.example.event.events;

import org.example.event.event_generic.AppEvent;
import org.example.model.Order;

// Order update edilmesi eventini oluşturalum, bu event oluştuğunda birazdan yazacağım service'de bu eventi dinleyip ona göre bir işlem yapacağız
public class UpdatedOrderEvent implements AppEvent<Order>{
    // order nesnesini tutalım
    private Order order;
    // contructor oluşturalım
    public UpdatedOrderEvent(Order order) {
        this.order = order;
    }
    // AppEvent arayüzündeki getSource metodunu implement edelim
    @Override
    public Order getSource() {
        return order;
    }
}
