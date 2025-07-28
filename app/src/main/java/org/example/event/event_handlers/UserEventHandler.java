package org.example.event.event_handlers;

import org.example.event.event_generic.AppEvent;
import org.example.event.event_generic.AppEventHandler;
import org.example.event.events.CreatedOrderEvent;
import org.example.event.events.CreatedUserEvent;
import org.example.event.events.LogoutUserEvent;
import org.example.model.User;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/*
b8rada yaptığımız tam olarak şu, şimdi biz daha önceden bir AppEvent generic interfacesi oluşturmuştuk, bu sınıfta ise
iste bu intergaceyi kullanarak @EventListener anatosyonunu kullanarak ilgili requestte publish edilen event hangi sınıfa
ait ise o sınıfa type casting ediliyor, sonra da gerekli işlemler yapılıyor, aayrıca burada soru işareti koyuyoruz çünkü
net bi AppEvent yok burada ya CreatedUserEvent.class ya da  LogoutUserEvent.class olacak ondan öyle koyuyoruz, mesela
bir örnek olarak şunu söyleyebilirim, create order requesti yaparsak controller'da ve o method içinde bir CreatedUserEvent oluşturursak
,o CreatedUserEvent de applicationEventPublisher.publishEvent() ile publish edersek bunu @EventListener yakalayacak ve buradaki
handler methodu çalışacak, type casting ile CreatedUserEvent olduğuna karar verecek yani bu sınıfın handler methodu çalışacak,
 */
@Component
public class UserEventHandler implements AppEventHandler<AppEvent<?>> {

    @Override
    @EventListener({CreatedUserEvent.class, LogoutUserEvent.class})
    public void handler(AppEvent<?> event) {
        if(event instanceof CreatedUserEvent){
            CreatedUserEvent createdUserEvent = (CreatedUserEvent) event;
            User user = createdUserEvent.getSource();
            System.out.println("User created brooo this is a event messageee: " + user.getUsername());
        }
        else if(event instanceof LogoutUserEvent){
            LogoutUserEvent logoutUserEvent = (LogoutUserEvent) event;
            User user = logoutUserEvent.getSource();
            System.out.println("User logout brooo this is a event messageee: " + user.getUsername());
        }
    }
}
