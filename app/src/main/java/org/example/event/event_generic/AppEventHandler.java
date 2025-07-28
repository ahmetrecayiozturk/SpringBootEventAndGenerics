package org.example.event.event_generic;
//burada AppEvent türünden extends edilen bir event'i handle edeceğiz bu yüzden extends ettik, bu sadece event handler için
//bu event handlerleri kullanarak birtakım şeyler yapacağız( ben basitçe log bastıracağım )
public interface AppEventHandler<E extends AppEvent<?>> {
    void handler(E event);
}
