package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 2/27/18.
 */

public class JoinGameEvent extends BaseEvent {

    public String userName;
    public int code;

    public JoinGameEvent(String username, int code) {
        this.type = EventType.JOIN_GAME;
        this.userName = username;
        this.code = code;
    }
}
