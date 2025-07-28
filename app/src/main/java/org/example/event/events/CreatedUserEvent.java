package org.example.event.events;

import org.example.event.event_generic.AppEvent;
import org.example.model.User;

// User oluşması eventini oluşturalum, bu event oluştuğunda birazdan yazacağım service'de bu eventi dinleyip ona göre bir işlem yapacağız
public class CreatedUserEvent implements AppEvent<User> {
    private final User user;
    public CreatedUserEvent(User user) {
        this.user = user;
    }
    @Override
    public User getSource() {
        return user;
    }
}
