package org.example.event.event_generic;
// Burada herhangi bir tür event üretebilmek için bir generics tanımlıyoruz, bu sadece event üretmek için event handler için değil
public interface AppEvent <E>{
    E getSource();
}
