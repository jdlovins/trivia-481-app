package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 2/7/18.
 */

public class LoginEvent extends BaseEvent {

    public String Username;
    public int UserID = -1;

    public LoginEvent(String username) {
        this.type = EventType.LOGIN;
        this.Username = username;
    }
}
