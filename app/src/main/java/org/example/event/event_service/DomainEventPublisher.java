package org.example.event.event_service;

import org.example.event.event_generic.AppEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


// Burada biz kendimiz direkt ApplicationEventPublisher kullanarak event publish edebeiliriz de ancak bunu başka bir seriviceye bağlayarak
//hem kkodu daha modelüler yaptık hem de geliştirmelere açık bıraktık, bunu controller'de kullanacağız
@Service
public class DomainEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    public DomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(AppEvent<?> appEvent) {
        applicationEventPublisher.publishEvent(appEvent);
    }
}
