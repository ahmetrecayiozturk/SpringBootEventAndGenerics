package org.example.event.event_handlers;

import org.example.event.event_generic.AppEvent;
import org.example.event.event_generic.AppEventHandler;
import org.example.event.events.CreatedOrderEvent;
import org.example.event.events.UpdatedOrderEvent;
import org.example.model.Order;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// Şimdi burada birden çok event'i handle edebilecek bir event handler oluşturacağız
/*
b8rada yaptığımız tam olarak şu, şimdi biz daha önceden bir AppEvent generic interfacesi oluşturmuştuk, bu sınıfta ise
iste bu intergaceyi kullanarak @EventListener anatosyonunu kullanarak ilgili requestte publish edilen event hangi sınıfa
ait ise o sınıfa type casting ediliyor, sonra da gerekli işlemler yapılıyor, aayrıca burada soru işareti koyuyoruz çünkü
net bi AppEvent yok burada ya CreatedOrderEvent.class ya da  UpdatedOrderEvent.class olacak ondan öyle koyuyoruz, mesela
bir örnek olarak şunu söyleyebilirim, create order requesti yaparsak controller'da ve o method içinde bir CreateOrderEvent oluşturursak
, o CreateOrdeEventi de applicationEventPublisher.publishEvent() ile publish edersek bunu @EventListener yakalayacak ve buradaki
handler methodu çalışacak, type casting ile CreateORderEvent olduğuna karar verecek yani bu sınıfın handler methodu çalışacak,
 */
@Component
public class OrderEventHandler implements AppEventHandler<AppEvent<?>> {

    @Override
    @EventListener({CreatedOrderEvent.class, UpdatedOrderEvent.class})
    public void handler(AppEvent<?> event) {
        if(event instanceof CreatedOrderEvent) {
            // Tipini bilmediğimiz için burada oluşturacağız, zaten eğer class tipi CreatedOrderEvent ise bu kesin type casting true olacak
            CreatedOrderEvent createdOrderEvent = (CreatedOrderEvent) event;
            // Şimdi de order'i alalım
            Order order = createdOrderEvent.getSource();
            // şimdi de bu order'i kullanarak bir log bastıralım
            System.out.println("Created Order broo this message is from ordereventhandler: " + order);
        }
        else if(event instanceof UpdatedOrderEvent) {
            // Tipini bilmediğimiz için burada oluşturacağız, zaten eğer class tipi UpdatedOrderEvent ise bu kesin type casting true olacak
            UpdatedOrderEvent updatedOrderEvent = (UpdatedOrderEvent) event;
            // Şimdi de order'i alalım
            Order order = updatedOrderEvent.getSource();
            // şimdi de bu order'i kullanarak bir log bastıralım
            System.out.println("Updated Order broo this message is from ordereventhandler: " + order);
        }

    }
}