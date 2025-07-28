package org.example.event.events;

import org.example.event.event_generic.AppEvent;
import org.example.model.User;

// User logout olması eventini oluşturalum, bu event oluştuğunda birazdan yazacağım service'de bu eventi dinleyip ona göre bir işlem yapacağız
public class LogoutUserEvent implements AppEvent<User> {
    private final User user;
    public LogoutUserEvent(User user) {
        this.user = user;
    }
    @Override
    public User getSource() {
        return user;
    }
}
